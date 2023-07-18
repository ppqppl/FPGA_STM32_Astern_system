#include "rtthread.h"
#include "main.h"
#include "usart.h"
#include "gpio.h"

#include "stdio.h"
#include "stdbool.h"
#include "time.h"

#include "SR04.h"
#include "key.h"
#include "bsp_esp8266_test.h"


// 数据

float distance = 0.0;
int key = 0;
bool start = false;
bool flag_5m = false;
bool if_start = false;

/*
// 重写 printf 函数，串口输出
int fputc(int ch,FILE *f)
{
		HAL_UART_Transmit(&huart1,(uint8_t *)&ch,1,0xFFFF);    
		//等待发送结束	
		while(__HAL_UART_GET_FLAG(&huart1,UART_FLAG_TC)!=SET){
		}		
		return ch;
}
*/

// 全局函数
void print_sing(void){
	if(start){
		
		
	}
}

struct rt_thread uart_out_thread;						// 设置线程
rt_uint8_t rt_uart_out_thread_stack[256];		// 设置栈空间

struct rt_thread get_SR04_thread;
rt_uint8_t rt_get_SR04_thread_stack[1024];

struct rt_thread key_thread;
rt_uint8_t rt_key_thread_stack[512];

struct rt_thread beep_thread;
rt_uint8_t rt_beep_thread_stack[1024];

void beep_ctrl(float distance);
//void beep_stop(void);

// 任务函数
void uart_out_task_entry(void *parameter);
void get_SR04_task_entry(void *parameter);
void key_task_entry(void *parameter);
void beep_task_entry(void *parameter);
//void beep_ctrl(float distance);

// 线程初始化函数
void MX_RT_Thread_Init(void){
	// 初始化线程，参数如下：（&进程， “进程名称”， 进程函数， RT_NULL, &栈起始位置， 栈大小， 3， 20）
	rt_thread_init(&uart_out_thread, "uart_out",  uart_out_task_entry, RT_NULL, &rt_uart_out_thread_stack[0], sizeof(rt_uart_out_thread_stack),3,20);
	rt_thread_init(&get_SR04_thread, "get_SR04",  get_SR04_task_entry, RT_NULL, &rt_get_SR04_thread_stack[0], sizeof(rt_get_SR04_thread_stack),3,20);
	rt_thread_init(&key_thread, "key",  key_task_entry, RT_NULL, &rt_key_thread_stack[0], sizeof(rt_key_thread_stack),3,20);
	rt_thread_init(&beep_thread, "beep",  beep_task_entry, RT_NULL, &rt_beep_thread_stack[0], sizeof(rt_beep_thread_stack),3,20);
	
	// 开启线程调度
	rt_thread_startup(&uart_out_thread);
	rt_thread_startup(&get_SR04_thread);
	rt_thread_startup(&key_thread);
	rt_thread_startup(&beep_thread);
	
}

// 主任务，需要循环执行
void MX_RT_Thread_Process(void){
	
	printf("\r\n开始运行 \r\n");
	//初始化ESP8266
  ESP8266_Init();
  //ESP8266进行透传模式
  ESP8266_StaTcpClient_Unvarnish_ConfigTest();
}

// 实例化任务函数
void uart_out_task_entry(void *parameter){
	while(1){
		distance = SR04_GetData();
		rt_thread_delay(50);
	}
}

void get_SR04_task_entry(void *paramater){
	while(1){
		rt_thread_delay(500);
		if(start){
			printf("%.2f\r\n",distance);
			char pStr [100];
			sprintf ( pStr, "%.2f", distance);
			ESP8266_print(pStr);
		}
	}
}

void key_task_entry(void *parameter){
	while(1){
		key = KEY_Scan(0);
//		printf("%d",key);
		if(key == 1){
			start = true;
			printf("start");
			ESP8266_print("start");
			key = 0;
		}
		else if(key == 2){
			start = false;
			printf("stop");
			ESP8266_print("stop");
			key = 0;
		}
		rt_thread_delay(30);
	}
}


void beep_task_entry(void *parameter){
	while(1){
		if(start == true)
		{
			int delay_time = distance * 15;
			
			if(distance>5){
				HAL_GPIO_WritePin(GPIOB,GPIO_PIN_8,GPIO_PIN_SET);
				rt_thread_delay(50);
				HAL_GPIO_WritePin(GPIOB,GPIO_PIN_8,GPIO_PIN_RESET);
				rt_thread_delay(delay_time);
			}
			else {
				HAL_GPIO_WritePin(GPIOB,GPIO_PIN_8,GPIO_PIN_SET);
				rt_thread_delay(10);
			}
			
			/*
			if(distance <= 3.00)
			{
				HAL_GPIO_WritePin(GPIOB,GPIO_PIN_8,GPIO_PIN_SET);
				rt_thread_delay(50);
			}
			else if(distance > 3.00 && distance <= 5.00)
			{
				HAL_GPIO_WritePin(GPIOB,GPIO_PIN_8,GPIO_PIN_SET);
				rt_thread_delay(50);
				HAL_GPIO_WritePin(GPIOB,GPIO_PIN_8,GPIO_PIN_RESET);
				rt_thread_delay(50);
			}
			else if(distance >5 && distance <= 10)
			{
				HAL_GPIO_WritePin(GPIOB,GPIO_PIN_8,GPIO_PIN_SET);
				rt_thread_delay(50);
				HAL_GPIO_WritePin(GPIOB,GPIO_PIN_8,GPIO_PIN_RESET);
				rt_thread_delay(100);
			}
			else if(distance >10 && distance <= 15)
			{
				HAL_GPIO_WritePin(GPIOB,GPIO_PIN_8,GPIO_PIN_SET);
				rt_thread_delay(100);
				HAL_GPIO_WritePin(GPIOB,GPIO_PIN_8,GPIO_PIN_RESET);
				rt_thread_delay(150);
			}
			else if(distance >15 && distance <= 20)
			{
				HAL_GPIO_WritePin(GPIOB,GPIO_PIN_8,GPIO_PIN_SET);
				rt_thread_delay(100);
				HAL_GPIO_WritePin(GPIOB,GPIO_PIN_8,GPIO_PIN_RESET);
				rt_thread_delay(200);
			}
			else if(distance >20 && distance <= 30)
			{
				HAL_GPIO_WritePin(GPIOB,GPIO_PIN_8,GPIO_PIN_SET);
				rt_thread_delay(100);
				HAL_GPIO_WritePin(GPIOB,GPIO_PIN_8,GPIO_PIN_RESET);
				rt_thread_delay(300);
			}
			
			else if(distance >30 && distance <= 40)
			{
				
				HAL_GPIO_WritePin(GPIOB,GPIO_PIN_8,GPIO_PIN_SET);
				rt_thread_delay(100);
				HAL_GPIO_WritePin(GPIOB,GPIO_PIN_8,GPIO_PIN_RESET);
				rt_thread_delay(400);
			}
			else if(distance >40 && distance <= 50)
			{
				HAL_GPIO_WritePin(GPIOB,GPIO_PIN_8,GPIO_PIN_SET);
				rt_thread_delay(100);
				HAL_GPIO_WritePin(GPIOB,GPIO_PIN_8,GPIO_PIN_RESET);
				rt_thread_delay(500);
			}
			
			else 
			{
				HAL_GPIO_WritePin(GPIOB,GPIO_PIN_8,GPIO_PIN_RESET);
				rt_thread_delay(100);
			}
			*/
		}
		else if(start == false)
		{
			HAL_GPIO_WritePin(GPIOB,GPIO_PIN_8,GPIO_PIN_RESET);
				rt_thread_delay(100);
		}
	}
}



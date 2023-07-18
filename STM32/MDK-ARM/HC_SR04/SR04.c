#include "SR04.h"

float distant;                 // 测量距离，保留两位小数
uint32_t measure_Buf[3] = {0}; // 存放定时器计数值的数组
float distances[5] = {0};
uint8_t measure_Cnt = 0; // 状态标志位
uint32_t high_time;      // 超声波模块返回的高电平时间
uint8_t n = 0;


//===============================================读取距离 高斯滤波
float SR04_GetData(void)
{
    switch (measure_Cnt)
    {
    case 0:
        TRIG_H;
        delay_us(30);
        TRIG_L;

        measure_Cnt++;
        __HAL_TIM_SET_CAPTUREPOLARITY(&htim2, TIM_CHANNEL_1, TIM_INPUTCHANNELPOLARITY_RISING);
        HAL_TIM_IC_Start_IT(&htim2, TIM_CHANNEL_1); // 启动输入捕获       或者: __HAL_TIM_ENABLE(&htim5);
        break;
    case 3:
        high_time = measure_Buf[1] - measure_Buf[0]; // 高电平时间
        distant = (high_time * 0.034) / 2;           // 单位cm
        distances[n] = distant;
        n++;
				if(distant == 0 || distant >= 280){
					n--;
				}
        if (n >= 5)
        { // 采用1、4、6、4、1的高斯核进行滤波
            n = 0;
            distant = (distances[0] + 4 * distances[1] + 6 * distances[2] + 4 * distances[3] + distances[4]) / 16;
        }
				else{
					
				}
        measure_Cnt = 0; // 清空标志位
        TIM2->CNT = 0;   // 清空计时器计数
				return distant;
        break;
    }
}


//===============================================读取距离 卡尔曼滤波


//===============================================us延时函数
void delay_us(uint32_t us) // 主频72M
{
    uint32_t delay = (HAL_RCC_GetHCLKFreq() / 4000000 * us);
    while (delay--)
    {
        ;
    }
}

//===============================================中断回调函数
void HAL_TIM_IC_CaptureCallback(TIM_HandleTypeDef *htim) //
{

    if (TIM2 == htim->Instance) // 判断触发的中断的定时器为TIM2
    {
        switch (measure_Cnt)
        {
        case 1:
            measure_Buf[0] = HAL_TIM_ReadCapturedValue(&htim2, TIM_CHANNEL_1);            // 获取当前的捕获值.
            __HAL_TIM_SET_CAPTUREPOLARITY(&htim2, TIM_CHANNEL_1, TIM_ICPOLARITY_FALLING); // 设置为下降沿捕获。
            measure_Cnt++;
            break;
        case 2:
            measure_Buf[1] = HAL_TIM_ReadCapturedValue(&htim2, TIM_CHANNEL_1); // 获取当前的捕获值.
            HAL_TIM_IC_Stop_IT(&htim2, TIM_CHANNEL_1);                         // 停止捕获   或者: __HAL_TIM_DISABLE(&htim5);
            measure_Cnt++;
        }
    }
}

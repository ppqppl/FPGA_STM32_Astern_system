#include "key.h"

int KEY_Scan(uint8_t mode)
{	 
	static int key_up=1;//按键松开标志位
	if(key_up&&(KEY1==0||KEY2==0))
	{
		//HAL_Delay(10);//去抖动
		/*
		int de = 0;
		while(de<2500){
			de ++;
		}
		*/
		rt_thread_delay(50);
		key_up=0;
		if(KEY1==0)return KEY1_PRES;
		else if(KEY2==0)return KEY2_PRES;
	}
	else if(KEY1==1||KEY2==1)key_up=1;
 	return 0;//无按键按下
}

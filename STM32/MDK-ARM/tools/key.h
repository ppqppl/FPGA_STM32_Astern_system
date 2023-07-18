#ifndef __KEY_H
#define __KEY_H
#include "main.h"
#include "tim.h"
#include "stdio.h"

#include "rtthread.h"


#define KEY1  HAL_GPIO_ReadPin(GPIOE,GPIO_PIN_3)//��ȡ����1
#define KEY2  HAL_GPIO_ReadPin(GPIOE,GPIO_PIN_2)//��ȡ����2

#define KEY1_PRES 1	//KEY1����
#define KEY2_PRES	2	//KEY2����

int KEY_Scan(uint8_t mode);

#endif

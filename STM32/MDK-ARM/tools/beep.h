#ifndef __BEEP_H

#define __BEEP_H

#include "main.h"

//LED 端口定义

#define BEEP PBout(8) // 蜂鸣器控制 IO

void BEEP_Init(void); //初始化

#endif

#include "beep.h"

//��ʼ�� PB8 Ϊ���.��ʹ��ʱ��

//LED IO ��ʼ��

void BEEP_Init(void)

{

GPIO_InitTypeDef GPIO_Initure;

__HAL_RCC_GPIOB_CLK_ENABLE(); //���� GPIOB ʱ��

GPIO_Initure.Pin=GPIO_PIN_8; //PB8

GPIO_Initure.Mode=GPIO_MODE_OUTPUT_PP; //�������

GPIO_Initure.Pull=GPIO_PULLUP; //����

GPIO_Initure.Speed=GPIO_SPEED_FREQ_HIGH; //����

HAL_GPIO_Init(GPIOB,&GPIO_Initure);

HAL_GPIO_WritePin(GPIOB,GPIO_PIN_8,GPIO_PIN_RESET);

//��������Ӧ���� GPIOB8 ���ͣ�

}

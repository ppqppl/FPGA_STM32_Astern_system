#include "bsp_esp8266_test.h"

volatile uint8_t ucTcpClosedFlag = 0;

/******************************************************************
*    !!!!! 注意：ESP8266 接收到的数据存储在数组 strEsp8266_Fram_Record .Data_RX_BUF 中，
*               需要解析数据，并做出对应的操作
******************************************************************/


/********************************************连接服务器*************************************************************************/

/**
  * @brief  ESP8266 StaTcpClient Unvarnish 配置测试函数
  * @param  无
  * @retval 无
  */



// 重写 printf
int fputc(int ch,FILE *f)
{
	uint8_t temp[1]={ch};
	LL_USART_TransmitData8(USART2,temp[0]);
	while(!LL_USART_IsActiveFlag_TXE(USART2));//需要等待发送完成
	return(ch);
}



void ESP8266_StaTcpClient_Unvarnish_ConfigTest(void)
{
  printf( "\r\n正在配置 ESP8266 ......\r\n" );
  printf( "\r\n使能ESP8266 ......\r\n" );
	macESP8266_CH_ENABLE();
	while( ! ESP8266_AT_Test() ){}

  printf( "\r\n正在配置工作模式: STA ......\r\n" );
	while( ! ESP8266_Net_Mode_Choose ( STA ) ){}

  printf( "\r\n正在连接 WiFi ......\r\n" );
  while( ! ESP8266_JoinAP ( macUser_ESP8266_ApSsid, macUser_ESP8266_ApPwd ) );	
	
  printf( "\r\n禁止多连接 ......\r\n" );
	while( ! ESP8266_Enable_MultipleId ( DISABLE ) ){}

  printf( "\r\n正在连接服务器 ......\r\n" );
//	while( !	ESP8266_Link_Server ( enumTCP, macUser_ESP8266_TcpServer_IP, macUser_ESP8266_TcpServer_Port, Single_ID_0 ) ){}
	while( !	ESP8266_Link_Server ( enumUDP, macUser_ESP8266_TcpServer_IP, macUser_ESP8266_TcpServer_Port, Single_ID_0 ) ){}

  printf( "\r\n进入透传发送模式 ......\r\n" );
	while( ! ESP8266_UnvarnishSend () );
	
	printf( "\r\n配置 ESP8266 完毕\r\n" );
			
	printf ( "\r\n开始透传......\r\n" );
}

/*************************************************************************************************************************/

/**
  * @brief  ESP8266 检查是否接收到了数据，检查连接和掉线重连
  * @param  无
  * @retval 无
  */
void ESP8266_CheckRecvDataTest(void)
{
  uint8_t ucStatus;

  if( strEsp8266_Fram_Record .InfBit .FramFinishFlag )   //接收到数据
  {
      //USART_ITConfig ( macESP8266_USARTx, USART_IT_RXNE, DISABLE ); //禁用串口接收中断
      LL_USART_DisableIT_RXNE(USART3);
      strEsp8266_Fram_Record .Data_RX_BUF [ strEsp8266_Fram_Record .InfBit .FramLength ]  = '\0';
      
      //使用USART1将esp8266接收到的数据通过串口调试助手打印出来
      printf("%s\n",strEsp8266_Fram_Record.Data_RX_BUF);

      //解析数据后，执行的操作函数在这里调用

      strEsp8266_Fram_Record .InfBit .FramLength = 0;                             //接收数据长度置零
      strEsp8266_Fram_Record.InfBit.FramFinishFlag = 0;                           //接收标志置零
      LL_USART_EnableIT_RXNE(USART3);         //使能串口接收中断
  }

  if ( ucTcpClosedFlag )                                             //检测是否失去连接
  {
    ESP8266_ExitUnvarnishSend ();                                    //退出透传模式
    
    do ucStatus = ESP8266_Get_LinkStatus ();                         //获取连接状态
    while ( ! ucStatus );
    
    if ( ucStatus == 4 )                                             //确认失去连接后重连
    {
      //正在重连热点和服务器
      printf ( "\r\nReconnecting to hotspot and server ......\r\n" );
      
      while ( ! ESP8266_JoinAP ( macUser_ESP8266_ApSsid, macUser_ESP8266_ApPwd ) );
      
      while ( !	ESP8266_Link_Server ( enumTCP, macUser_ESP8266_TcpServer_IP, macUser_ESP8266_TcpServer_Port, Single_ID_0 ) );
      
      //重连热点和服务器成功
      printf ( "\r\nReconnect to hotspot and server successfully\r\n" );

    }
    
    while ( ! ESP8266_UnvarnishSend () );		
    
  }
}

void ESP8266_print(char * pStr){
	while(!ESP8266_SendString(ENABLE,pStr,0,Single_ID_0)){}
}

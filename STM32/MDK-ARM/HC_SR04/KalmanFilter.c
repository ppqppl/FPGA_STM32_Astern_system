#include "KalmanFilter.h"

/*�������˲���ֵ����*/
float P = 1;
float P_; // ��Ӧ��ʽ�е�p'
float X = 0;
float X_; // X'
float K = 0;
float Q = 0.01; // ����
// float R=0.2;  //R����ܴ󣬸�����Ԥ��ֵ����ô��������Ӧ�ͻ�ٶۣ���֮�෴
float R = 0.5;
float distance0 = 0;
float distance1 = 0;
/*����������*/
float KLM(float Z)
{
    X_ = X + 0;
    P_ = P + Q;
    K = P_ / (P_ + R);
    X = X_ + K * (Z - X_);
    P = P_ - K * P_;
    return X;
}
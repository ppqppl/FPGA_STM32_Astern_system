//package com.example.ppqppl_inpatient_ward_server.utils;
//
//import java.math.BigDecimal;
//import java.util.Scanner;
//
//public class Car_Score {
//	private double distance;
//	public static double score(double d) {
//		double result = 0.0;
//		if(d==10) {
//			System.out.println("100.0");
//			return 100.0;
//		}
//		else if(d>=2&&d<3) {
//			double data=60.0-(10.0-d)/10.0;
//			result = new BigDecimal(data).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
//			System.out.print(result);
//			System.out.println("不合格");
//		}
//		else if(d>=3&&d<=4) {
//			double data0=85.0-(10.0-d)/10.0;
//			result = new BigDecimal(data0).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
//		}
//		else if(d>4&&d<5) {
//			double data1=80.0-(10.0-d)/10.0;
//			result = new BigDecimal(data1).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
//		}
//		else if(d>=5&&d<10){
//			double data2=100.0-(10.0-d)/10.0;
//			result = new BigDecimal(data2).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
//		}
//		else if(d>10&&d<=15){
//			double data3=100.0-(10.0-d)/10.0;
//			result = new BigDecimal(data3).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
//		}
//		else if(d>15&&d<=30) {
//			double data4=98.0-(d-10.0)/10.0;
//			result = new BigDecimal(data4).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
//		}
//		else if(d>30&&d<=50) {
//			double data5=94.0-(d-10.0)/10.0;
//			result = new BigDecimal(data5).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
//		}
//		else if(d>50&&d<=80) {
//			double data6=90.0-(d-10.0)/10.0;
//			result = new BigDecimal(data6).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
//		}
//		else if(d>80&&d<=120) {
//			double data7=86.0-(d-10.0)/10.0;
//			result = new BigDecimal(data7).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
//		}
//		else if(d>120&&d<=160) {
//			double data8=82.0-(d-10.0)/10.0;
//			result = new BigDecimal(data8).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
//		}
//		else if(d>160&&d<=200) {
//			double data9=79.0-(d-10.0)/10.0;
//			result = new BigDecimal(data9).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
//		}
//		else if(d>200) {
//			double data10=60.0-(d-10.0)/10.0;
//			result = new BigDecimal(data10).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
//			System.out.println("不合格");
//		}
//		System.out.println("倒车成绩 :  "+Double.toString(result));
//		return result;
//
//	}
//
////	public static void main(String[] args) {
////		// TODO Auto-generated method stub
////		while(true) {
////			Scanner scanner=new Scanner (System.in);
////			System.out.println("请输入库角与车身间距：");
////			double dis=scanner.nextDouble();
////			score(dis);
////		}
////
////	}
//
//}


package com.example.ppqppl_inpatient_ward_server.utils;

import java.math.BigDecimal;
import java.util.Scanner;

public class Car_Score {
	private double distance;
	public static double score(double d) {
		double result = 0.0;
		if(d<=8&&d>=7) {
			System.out.println("100.0");
			return 100.0;
		}
		else if(d>50||d<7) {
			System.out.println("0.0(不及格)");
		}
		else if(d>8&&d<=15) {
			double data0=100-10*((d-8.0)/7);
			result = new BigDecimal(data0).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
		else if(d>15&&d<=25) {
			double data1=90-(d-15);
			result = new BigDecimal(data1).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
		else if(d>25&&d<=35){
			double data2=80-(d-25);
			result = new BigDecimal(data2).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
		else if(d>35&&d<=50){
			double data3=70-10*((d-35)/15);
			result = new BigDecimal(data3).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
		}

		System.out.println("最终得分 :  "+Double.toString(result));
		return result;

	}

//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		while(true) {
//			Scanner scanner=new Scanner (System.in);
//			System.out.println("鐠囩柉绶崗銉ョ氨鐟欐帊绗屾潪锕侀煩闂傜绐涢敍锟�");
//			double dis=scanner.nextDouble();
//			score(dis);
//		}
//
//	}

}

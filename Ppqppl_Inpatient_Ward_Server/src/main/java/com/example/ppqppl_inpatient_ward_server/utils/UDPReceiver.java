package com.example.ppqppl_inpatient_ward_server.utils;

import lombok.ToString;
import org.springframework.web.client.RestTemplate;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class UDPReceiver {

    public static String mmm = "000";

    // 端口
    private static int port = 6000;

    // 间隔时间(ms)
    private static int time = 50;

    public static void receive() {
        try (// 创建DatagramSocket对象
             DatagramSocket socket = new DatagramSocket(port);) {
            // 创建接收数据的缓冲区
            byte[] buffer = new byte[1024];
            // 创建DatagramPacket对象
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            // RestTemplate
            RestTemplate template = new RestTemplate();
            while (true) {
                Thread.sleep(time);
                // 接收数据
                socket.receive(packet);
                // 处理接收到的数据
                String message = new String(packet.getData(), 0, packet.getLength(),"utf-8");
                LocalDateTime time = LocalDateTime.now();
                String formattedTime = time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                String content = "当前时间:" + formattedTime + "    接收到的数据：" + message;
                mmm = message;

//                Object o = template.getForObject("http://localhost:6001/test", Object.class);
//                String o = template.getForObject("http://localhost:6001/hello", String.class);
//                System.out.println(o);
                System.out.println(content);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

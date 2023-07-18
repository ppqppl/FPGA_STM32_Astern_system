package com.example.ppqppl_inpatient_ward_server;

import com.example.ppqppl_inpatient_ward_server.utils.UDPReceiver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@SpringBootApplication
public class PpqpplInpatientWardServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(PpqpplInpatientWardServerApplication.class, args);
        new Thread(()->{
            UDPReceiver.receive();
        }).start();
    }


}

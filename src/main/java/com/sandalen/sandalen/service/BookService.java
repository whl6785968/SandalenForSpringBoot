package com.sandalen.sandalen.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class BookService {
//    @RabbitListener(queues = "atguigu.emps")
    public void listenAtguigu(Map map){
        System.out.println(map);
    }
}

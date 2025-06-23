package com.example.controller;


import com.example.model.domain.Order;
import com.example.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/appointments")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/createOrder")
    public Order createOrder(@RequestParam String studentId, @RequestParam String teacherId ,
                             @RequestParam("startTime") LocalDateTime startTime,
                             @RequestParam("endTime")LocalDateTime  endTime) {
        return orderService.createOrder(studentId, teacherId, startTime, endTime);
    }
}

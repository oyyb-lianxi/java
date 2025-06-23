package com.example.service;

import com.example.model.domain.Order;

import java.time.LocalDateTime;

public interface OrderService {

  public Order createOrder(String studentId, String teacherId, LocalDateTime startTime, LocalDateTime endTime);
}

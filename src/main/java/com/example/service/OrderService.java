package com.example.service;

public interface OrderService {

  public Order createOrder(Long userId, Long teacherId, Date startTime, Date endTime);
}

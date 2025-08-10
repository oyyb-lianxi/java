package com.example.service;

import com.example.model.dto.OrderDto;
import com.example.model.entity.Result;
import com.example.model.vo.OrderVo;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface OrderService {

    Result createOrder(String studentId, String teacherId, LocalDateTime startTime, LocalDateTime endTime, BigDecimal amount);

    Boolean updateOrder(OrderDto orderDto);

    List<OrderVo> getOrdersByConditions(OrderDto orderDto);

    Integer countOrdersByConditions(OrderDto orderDto);
}

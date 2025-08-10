package com.example.service.impl;

import com.example.mapper.OrderMapper;
import com.example.model.CommonUtils.EnumUtils;
import com.example.model.CommonUtils.StringUtils;
import com.example.model.dict.OrderStatus;
import com.example.model.domain.Order;
import com.example.model.dto.OrderDto;
import com.example.model.entity.Result;
import com.example.model.vo.OrderVo;
import com.example.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public Result createOrder(String studentId, String teacherId, LocalDateTime startTime, LocalDateTime endTime, BigDecimal amount) {

      Result result =new Result();

      Order order = new Order();
      order.setOrderId(StringUtils.generateOrderId());
      order.setStudentId(studentId);
      order.setTeacherId(teacherId);
      order.setStartTime(startTime);
      order.setEndTime(endTime);
      order.setAmount(amount);
      order.setStatus(OrderStatus.PENDING_CONFIRMATION.getCode());

      result.setCode(200);
      result.setMsg("创建订单成功");
      result.setData(order);
      return result;
    }

    @Override
    public Boolean updateOrder(OrderDto orderDto) {
        int i = orderMapper.updateOrder(orderDto);
        if(i==1){
            return true;
        }
        return false;
    }

    @Override
    public List<OrderVo> getOrdersByConditions(OrderDto orderDto) {
        List<OrderVo> orders = orderMapper.getOrdersByConditions(orderDto);
        for (OrderVo order : orders) {
            order.setStatusDesc(EnumUtils.getDescByCode(OrderStatus.class,order.getStatus()));
        }
        return orders;
    }

    @Override
    public Integer countOrdersByConditions(OrderDto orderDto) {
        return orderMapper.countOrdersByConditions(orderDto);
    }
}


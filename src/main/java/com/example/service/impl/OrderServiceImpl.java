package com.example.service.impl;

import com.example.model.domain.Order;
import com.example.service.OrderService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

@Service
public class OrderServiceImpl implements OrderService {
  @Override
      public Order createOrder(String studentId, String teacherId, LocalDateTime startTime, LocalDateTime endTime) {
      //查询学生跟家长是否存在

//        if(user.isMember()) {
//            throw new RuntimeException("User is not a member");
//        }
//
//        if(teacher.getAvailability().contains(startTime.toString())) {
//            throw new RuntimeException("Teacher not at the specified time");
//        }

      Order order = new Order();
      order.setStudentId(studentId);
      order.setTeacherId(teacherId);
//        order.setOrderTime(new Date());
      order.setStartTime(startTime);
      return order;
  }
}


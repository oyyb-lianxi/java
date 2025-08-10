package com.example.mapper;

import com.example.model.domain.Order;
import com.example.model.dto.OrderDto;
import com.example.model.vo.OrderVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderMapper {

    int saveOrder(Order order);
    int updateOrder(Order order);

    Integer countOrdersByConditions(OrderDto orderDto);
    List<OrderVo> getOrdersByConditions(OrderDto orderDto);
}

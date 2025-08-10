package com.example.controller;


import com.example.model.dto.OrderDto;
import com.example.model.entity.Result;
import com.example.model.vo.OrderVo;
import com.example.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/appointments")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/createOrder")
    public Result createOrder(@RequestParam String studentId, @RequestParam String teacherId ,
                              @RequestParam("startTime") LocalDateTime startTime,
                              @RequestParam("startTime") LocalDateTime endTime,
                              @RequestParam("amount") BigDecimal amount) {
        return orderService.createOrder(studentId, teacherId, startTime, endTime, amount);
    }

    /**
     * 修改订单信息
     */
    @PostMapping("/updateOrder")
    public Result updateOrder(@RequestBody OrderDto orderDto){
        Result result =new Result();
        Boolean aBoolean = orderService.updateOrder(orderDto);
        result.setCode(200);
        if(aBoolean){
            result.setMsg("修改成功");
        }else {
            result.setMsg("修改失败");
        }
        return result;
    }

    /**
     * 按条件查询订单信息
     */
    @PostMapping("/getOrdersByConditions/{page}/{pageSize}")
    public Result getOrdersByConditions(@RequestBody OrderDto orderDto,
                                        @PathVariable Integer page,@PathVariable Integer pageSize){
        Result result = new Result();
        log.info(orderDto+"orderDto");
        Map<String,Object> map =new HashMap();
        orderDto.setOffSet((page-1) * pageSize);
        orderDto.setPageSize(pageSize);
        List<OrderVo> studentsByConditions = orderService.getOrdersByConditions(orderDto);
        if(page!=null){
            Integer  countTeachersByConditions= orderService.countOrdersByConditions(orderDto);
            map.put("total",countTeachersByConditions);
        }
        map.put("students",studentsByConditions);
        result.setCode(200);
        result.setData(map);
        return result;
    }
}

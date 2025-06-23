package com.example.controller;


@RestController
@RequestMapping("/appointments")  
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/createOrder")
    public Order createOrder(@RequestParam Long studentId, @RequestParam Long teacherId teacherId, @RequestParam("startTime" startTime, @RequestParam("endTime" endTime endTime) {
        return orderService.createOrder(userId, teacherId, startTime, endTime);
    }
}

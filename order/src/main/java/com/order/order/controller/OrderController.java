package com.order.order.controller;

import com.order.order.model.Orders;
import com.order.order.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {


    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    private OrderService orderService;
    public  OrderController(OrderService orderService){
        this.orderService=orderService;
    }
    @PostMapping("/orders")
    public Orders createOrder(@RequestBody Orders order){
        return orderService.saveOrder(order);
    }
    @GetMapping("/orders")
    public List<Orders> listOrders(@RequestParam String customerNumber){
        return orderService.listOrders(customerNumber);
    }

}

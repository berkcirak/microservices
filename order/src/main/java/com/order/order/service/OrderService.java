package com.order.order.service;

import com.order.order.model.Orders;
import com.order.order.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    private OrderRepository orderRepository;
    private OrderMessageService orderMessageService;
    public OrderService(OrderRepository orderRepository, OrderMessageService orderMessageService){
        this.orderRepository=orderRepository;
        this.orderMessageService=orderMessageService;
    }

    public Orders saveOrder(Orders orders){
        LocalDateTime currentDateTime = LocalDateTime.now();
        orders.setOrderDate(currentDateTime);
        Orders createdOrders = orderRepository.save(orders);
        orderMessageService.sendMessage(orders);
        return createdOrders;
    }

    public List<Orders> listOrders(String customerNumber){
        return orderRepository.findByCustomerNumber(customerNumber);
    }




}

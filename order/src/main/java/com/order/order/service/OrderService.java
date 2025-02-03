package com.order.order.service;

import com.order.order.model.Order;
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

    public Order saveOrder(Order order){
        LocalDateTime currentDateTime = LocalDateTime.now();
        order.setOrderDate(currentDateTime);
        Order createdOrder = orderRepository.save(order);
        orderMessageService.sendMessage(order);
        return createdOrder;
    }

    public List<Order> listOrders(String customerNumber){
        return orderRepository.findByCustomerNumber(customerNumber);
    }




}

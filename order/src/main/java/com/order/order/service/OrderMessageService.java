package com.order.order.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.order.order.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderMessageService {

    private static final Logger log = LoggerFactory.getLogger(OrderMessageService.class);

    private ObjectMapper objectMapper;
    private RabbitTemplate rabbitTemplate;
    public OrderMessageService(ObjectMapper objectMapper, RabbitTemplate rabbitTemplate){
        this.objectMapper=objectMapper;
        this.rabbitTemplate=rabbitTemplate;
    }

    private final String queueName = "orderQueue";

    public void sendMessage(Order order){
        try{
            String message = objectMapper.writeValueAsString(order);
            rabbitTemplate.convertAndSend(queueName, message);
            log.info("Message sent to RabbitMQ: " + message);
        } catch (JsonProcessingException e){
            e.printStackTrace();
        }
    }


}

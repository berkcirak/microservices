package com.order.order.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonProperty("customer_name")
    private String customerNumber;
    @JsonProperty("product_id")
    private String productId;
    @JsonProperty("quantity")
    private int quantity;
    @JsonProperty("order_date")
    private LocalDateTime orderDate;

    public void setOrderDate(LocalDateTime orderDate){
        this.orderDate=orderDate;
    }
    public LocalDateTime getOrderDate(){
        return this.orderDate;
    }

}

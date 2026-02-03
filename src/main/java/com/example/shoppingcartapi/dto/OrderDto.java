package com.example.shoppingcartapi.dto;

import com.example.shoppingcartapi.enums.OrderStatus;
import com.example.shoppingcartapi.model.OrderItem;
import com.example.shoppingcartapi.model.User;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class OrderDto {
    private Long id;
    private Long userId;
    private LocalDateTime orderDate;
    private BigDecimal totalAmount;
    private String status;
    private List<OrderItemDto> items;
}

package com.example.shoppingcartapi.service.order;

import com.example.shoppingcartapi.model.Order;

import java.util.List;

public interface IOrderService {
    Order placeOrder(Long userId); // after implement user stuff replace OrderItem userId
    Order getOrder(Long orderId);

    List<Order> getUserOrder(Long userId);
}

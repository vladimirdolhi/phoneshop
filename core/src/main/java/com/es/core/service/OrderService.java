package com.es.core.service;

import com.es.core.model.cart.Cart;
import com.es.core.model.order.Order;
import com.es.core.exception.OutOfStockException;
import com.es.core.model.order.OrderStatus;


import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface OrderService {

    List<Order> findAll();
    Order get(UUID uuid);
    Order get(Long id);
    Order createOrder(Cart cart);
    void placeOrder(Order order) throws OutOfStockException;
    void updateStatus(Long id, OrderStatus status);
    BigDecimal getDeliveryPrice();
}

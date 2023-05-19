package com.es.core.service.impl;

import com.es.core.exception.OrderNotFoundException;
import com.es.core.model.cart.Cart;
import com.es.core.model.order.Order;
import com.es.core.exception.OutOfStockException;
import com.es.core.model.order.OrderDao;
import com.es.core.model.order.OrderItem;
import com.es.core.model.order.OrderStatus;
import com.es.core.model.phone.Stock;
import com.es.core.service.CartService;
import com.es.core.service.OrderService;
import com.es.core.service.PhoneService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderDao orderDao;

    @Resource
    private PhoneService phoneService;

    @Resource
    private CartService cartService;

    @Value("${delivery.price}")
    private BigDecimal deliveryPrice;

    @Value("${place.order.fail.msg}")
    private String warningMsg;

    @Override
    public List<Order> findAll() {
        return orderDao.findAll();
    }

    @Override
    public Order get(UUID uuid) {
        return orderDao.getBySecureId(uuid).orElseThrow(
                ()-> new OrderNotFoundException("Order with UUID = " + uuid + " not found"));
    }

    @Override
    public Order get(Long id) {
        return orderDao.getById(id).orElseThrow(
                ()-> new OrderNotFoundException("Order with id = " + id + " not found"));
    }

    @Override
    public Order createOrder(Cart cart) {
        Order order = Order.builder().subtotal(cart.getTotalCost())
                .deliveryPrice(deliveryPrice)
                .totalPrice(cart.getTotalCost().add(deliveryPrice))
                .creationDate(LocalDateTime.now())
                .status(OrderStatus.NEW)
                .build();

        order.setOrderItems(cart.getItems().stream()
                .map(cartItem -> new OrderItem(cartItem.getPhone(), cartItem.getQuantity())).
                collect(Collectors.toList()));

        return order;
    }

    @Override
    @Transactional
    public void placeOrder(Order order) throws OutOfStockException {
        checkOrder(order);
        orderDao.save(order);
    }

    @Override
    public void updateStatus(Long id, OrderStatus status) {
        orderDao.updateStatus(id, status);
    }

    private void checkOrder(Order order) {

        List<OrderItem> orderItems = order.getOrderItems();
        List<OrderItem> rejectedItems = new ArrayList<>();
        rejectOutOfStockItems(orderItems, rejectedItems);

        if (!rejectedItems.isEmpty()) {
            removeRejectedItemsFromCart(rejectedItems);
            cartService.recalculateCart();
            String message = buildOutOfStockMessage(rejectedItems);
            throw new OutOfStockException(message);
        } else {
            cartService.clearCart();
        }

        orderItems.forEach(this::updateStock);
    }

    private void updateStock(OrderItem orderItem) {
        Stock stock = phoneService.getStock(orderItem.getPhone().getId());
        stock.setPhone(orderItem.getPhone());
        stock.setStock((int) (stock.getStock() - orderItem.getQuantity()));
        phoneService.updateStock(stock);
    }

    private void rejectOutOfStockItems(List<OrderItem> orderItems, List<OrderItem> rejectedItems) {

        orderItems.forEach(orderItem -> {
            Stock stock = phoneService.getStock(orderItem.getPhone().getId());
            if (orderItem.getQuantity() > stock.getStock() - stock.getReserved()){
                rejectedItems.add(orderItem);
            }
        });

        orderItems.removeAll(rejectedItems);
    }

    private void removeRejectedItemsFromCart(List<OrderItem> rejectedItems) {
        rejectedItems.forEach(rejectedItem -> cartService.getCart().getItems().
                removeIf(cartItem -> cartItem.getPhone().getId().equals(rejectedItem.getPhone().getId())));
    }


    private String buildOutOfStockMessage(List<OrderItem> rejectedItems) {
        StringBuilder stringBuilder = new StringBuilder(warningMsg);
        rejectedItems.forEach(rejectedItem -> stringBuilder.append(rejectedItem.getPhone().getModel()));

        return stringBuilder.toString();
    }

    public BigDecimal getDeliveryPrice() {
        return deliveryPrice;
    }
}

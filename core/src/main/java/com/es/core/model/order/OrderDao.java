package com.es.core.model.order;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderDao {
    List<Order> findAll();
    Optional<Order> getById(Long id);
    Optional<Order> getBySecureId(UUID uuid);
    void save(Order order);
    void updateStatus(Long id, OrderStatus orderStatus);
}

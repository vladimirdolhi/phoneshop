package com.es.core.model.order;

import java.util.Optional;
import java.util.UUID;

public interface OrderDao {
    Optional<Order> getBySecureId(UUID uuid);

    void save(Order order);
}

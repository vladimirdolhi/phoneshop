package com.es.core.cart;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class HttpSessionCartService implements CartService {
    @Override
    public Cart getCart() {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public void addPhone(Long phoneId, Long quantity) {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public void update(Map<Long, Long> items) {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public void remove(Long phoneId) {
        throw new UnsupportedOperationException("TODO");
    }
}

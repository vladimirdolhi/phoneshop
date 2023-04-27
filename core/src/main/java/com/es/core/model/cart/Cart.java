package com.es.core.model.cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Cart {
    private List<CartItem> items;
    private long totalQuantity;
    private BigDecimal totalCost;

    public Cart() {
        items = new ArrayList<>();
        totalQuantity = 0L;
        totalCost = BigDecimal.valueOf(0);
    }

}

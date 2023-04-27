package com.es.phoneshop.web.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class CartInfoResponse {
    private long totalQuantity;
    private BigDecimal totalCost;
}

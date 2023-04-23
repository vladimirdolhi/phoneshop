package com.es.phoneshop.web.payload.request;

import com.es.phoneshop.web.annotation.InStockQuantity;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@InStockQuantity
public class AddToCartRequest {
    @Min(value = 1)
    @NotNull
    private Long phoneId;

    @Min(value = 1, message = "Must be positive amount")
    @Max(value = 100, message = "Limit 100 items")
    @NotNull
    private Long quantity;
}

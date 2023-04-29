package com.es.phoneshop.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDto {
    @NotNull
    private Long phoneId;

    @Min(value = 1, message = "Must be positive amount")
    @NotNull
    private Long quantity;
}

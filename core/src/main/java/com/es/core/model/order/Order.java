package com.es.core.model.order;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order
{
    private Long id;
    private UUID secureId;
    private List<OrderItem> orderItems;
    private BigDecimal subtotal;
    private BigDecimal deliveryPrice;
    private BigDecimal totalPrice;
    private LocalDateTime creationDate;

    private String firstName;
    private String lastName;
    private String deliveryAddress;
    private String contactPhoneNo;
    private String additionalInfo;

    private OrderStatus status;

}

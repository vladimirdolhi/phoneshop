package com.es.core.model.order;

import java.math.BigDecimal;
import java.util.List;

public class Order
{
    private Long key;
    private List<OrderItem> orderItems;
    /**
     *  A sum of order item prices;
     */
    private BigDecimal subtotal;
    private BigDecimal deliveryPrice;
    /**
     * <code>subtotal</code> + <code>deliveryPrice</code>
     */
    private BigDecimal totalPrice;

    private String firstName;
    private String lastName;
    private String deliveryAddress;
    private String contactPhoneNo;
}

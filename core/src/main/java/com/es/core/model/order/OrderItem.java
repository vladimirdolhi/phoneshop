package com.es.core.model.order;

import com.es.core.model.phone.Phone;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {
    private Long id;
    private Phone phone;
    private Long quantity;
}

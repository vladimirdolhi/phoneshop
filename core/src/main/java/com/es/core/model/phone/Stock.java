package com.es.core.model.phone;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Stock {
    private Phone phone;
    private Integer stock;
    private Integer reserved;

}

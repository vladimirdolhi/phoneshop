package com.es.phoneshop.web.util.validation;

import com.es.core.model.phone.Stock;
import com.es.core.service.PhoneService;
import com.es.phoneshop.web.dto.CartDto;
import com.es.phoneshop.web.dto.CartItemDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class UpdateCartDtoValidator implements Validator {

    private final PhoneService phoneService;

    @Override
    public boolean supports(Class<?> aClass) {
        return UpdateCartDtoValidator.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        CartDto cartDto = (CartDto) o;
        List<Long> rejectIds = new ArrayList<>();
        for (int i = 0; i < cartDto.getItems().size(); i++) {
            CartItemDto item = cartDto.getItems().get(i);
            Long id = item.getPhoneId();
            Long requestedQuantity = item.getQuantity();

            if (requestedQuantity == null) {
                errors.rejectValue("items['" + i + "'].phoneId", "invalid.quantity",
                        "Invalid quantity");
                rejectIds.add(id);
            } else {
                if (requestedQuantity < 1) {
                    errors.rejectValue("items['" + i + "'].phoneId", "invalid.quantity",
                            "Must be positive amount");
                    rejectIds.add(id);
                }

                Stock stock = phoneService.getStock(id);
                if (requestedQuantity > stock.getStock() - stock.getReserved()) {
                    errors.rejectValue("items['" + i + "'].phoneId", "out.of.stock.quantity",
                            "Out of stock");
                    rejectIds.add(id);
                }
            }
        }

        rejectIds.forEach(id -> cartDto.getItems().removeIf(item -> item.getPhoneId().equals(id)));
    }
}

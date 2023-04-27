package com.es.phoneshop.web.util.validation;

import com.es.core.model.phone.Stock;
import com.es.core.service.PhoneService;
import com.es.phoneshop.web.payload.request.AddToCartRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class AddToCartQuantityValidator implements Validator {
    private final PhoneService phoneService;

    @Autowired
    public AddToCartQuantityValidator(PhoneService phoneService) {
        this.phoneService = phoneService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return AddToCartRequest.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        AddToCartRequest request = (AddToCartRequest) o;
        Stock stock = phoneService.getStock(request.getPhoneId());
        if (request.getQuantity() > stock.getStock() - stock.getReserved()){
            errors.rejectValue("quantity", "Out of stock");
        }
    }
}

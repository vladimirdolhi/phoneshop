package com.es.phoneshop.web.util.validation;


import com.es.core.model.phone.Stock;
import com.es.core.service.PhoneService;
import com.es.phoneshop.web.annotation.InStockQuantity;
import com.es.phoneshop.web.payload.request.AddToCartRequest;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class QuantityValidator implements ConstraintValidator<InStockQuantity, Object> {

    private final PhoneService phoneService;

    @Autowired
    public QuantityValidator(PhoneService phoneService) {
        this.phoneService = phoneService;
    }
    @Override
    public void initialize(InStockQuantity constraintAnnotation) {

    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext constraintValidatorContext) {
        AddToCartRequest request = (AddToCartRequest) obj;
        Stock stock = phoneService.getStock(request.getPhoneId());
        return  request.getQuantity() < stock.getStock() - stock.getReserved();
    }
}

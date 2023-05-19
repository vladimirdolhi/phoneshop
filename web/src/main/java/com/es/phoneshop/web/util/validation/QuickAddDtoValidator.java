package com.es.phoneshop.web.util.validation;

import com.es.core.exception.ProductNotFoundException;
import com.es.core.model.phone.Stock;
import com.es.core.service.PhoneService;
import com.es.phoneshop.web.dto.QuickAddDto;
import com.es.phoneshop.web.dto.QuickAddItemDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class QuickAddDtoValidator implements Validator {

    private final PhoneService phoneService;


    @Override
    public boolean supports(Class<?> aClass) {
        return QuickAddDtoValidator.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        QuickAddDto quickAddDto = (QuickAddDto) o;
        List<String> rejectedModels = new ArrayList<>();

        for (int i = 0; i < quickAddDto.getItems().size(); i++) {
            QuickAddItemDto item = quickAddDto.getItems().get(i);
            String model = item.getModel();
            String requestedQuantity = item.getQuantity();
            checkModel(model, requestedQuantity, errors, i, rejectedModels);
        }

        rejectedModels.forEach(model -> quickAddDto.getItems().removeIf(item -> item.getModel().equals(model)));
    }

    private void checkModel(String model, String requestedQuantity, Errors errors, int i, List<String> rejectedModels) {

        if (model == null || model.equals("")){
            rejectedModels.add(model);
            return;
        }
        try {
            phoneService.getByModel(model);
            checkQuantity(model, requestedQuantity, errors, i, rejectedModels);
        } catch (ProductNotFoundException e){
            errors.rejectValue("items['" + i + "'].model", "invalid.model",
                    "Product not found");
            rejectedModels.add(model);
        }
    }

    private void checkQuantity(String model, String requestedQuantity, Errors errors, int i, List<String> rejectedModels) {
        try{
            long quantity = Long.parseLong(requestedQuantity);

            if (quantity < 1) {
                errors.rejectValue("items['" + i + "'].quantity", "invalid.quantity",
                        "Must be positive amount");
                rejectedModels.add(model);
            }

            Stock stock = phoneService.getStock(phoneService.getByModel(model).getId());

            if (quantity > stock.getStock() - stock.getReserved()) {
                errors.rejectValue("items['" + i + "'].quantity", "out.of.stock.quantity",
                        "Out of stock");
                rejectedModels.add(model);
            }

        } catch (NumberFormatException ex){
            errors.rejectValue("items['" + i + "'].quantity", "invalid.quantity",
                    "Invalid quantity");
            rejectedModels.add(model);
        }
    }


}

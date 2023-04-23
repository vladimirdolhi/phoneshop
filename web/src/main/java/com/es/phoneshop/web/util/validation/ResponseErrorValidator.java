package com.es.phoneshop.web.util.validation;

import com.es.phoneshop.web.exception.InvalidRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class ResponseErrorValidator {

    public ResponseEntity<Object> mapValidationService(BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();

            if (!CollectionUtils.isEmpty(result.getAllErrors())) {
                for (ObjectError error : result.getAllErrors()) {
                    errorMap.put(error.getCode(), error.getDefaultMessage());
                }
            }

            for (FieldError error : result.getFieldErrors()) {
                errorMap.put(error.getField(), error.getDefaultMessage());
            }

            StringBuilder msg = new StringBuilder();
            errorMap.entrySet().stream().map(Map.Entry::getValue).
                    filter(Objects::nonNull).distinct().forEach(v -> msg.append(v).append("\n"));
            return new ResponseEntity<>(new InvalidRequestException(msg.toString()), HttpStatus.BAD_REQUEST);
        }

        return null;
    }
}

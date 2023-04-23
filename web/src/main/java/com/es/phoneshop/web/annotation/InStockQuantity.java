package com.es.phoneshop.web.annotation;

import com.es.phoneshop.web.util.validation.QuantityValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = QuantityValidator.class)
@Documented
public @interface InStockQuantity {

    String message() default "Out of stock";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

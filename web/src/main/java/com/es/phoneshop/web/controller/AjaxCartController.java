package com.es.phoneshop.web.controller;

import com.es.core.model.cart.Cart;
import com.es.core.service.CartService;
import com.es.phoneshop.web.exception.InvalidRequestException;
import com.es.phoneshop.web.payload.request.AddToCartRequest;
import com.es.phoneshop.web.payload.response.CartInfoResponse;
import com.es.phoneshop.web.util.validation.AddToCartQuantityValidator;
import com.es.phoneshop.web.util.validation.ResponseErrorUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;


@RestController
@RequestMapping(value = "/ajaxCart")
public class AjaxCartController {
    @Resource
    private CartService cartService;

    @Resource
    private ResponseErrorUtil responseErrorValidator;
    @Resource
    private AddToCartQuantityValidator quantityValidator;

    @PostMapping(value = "/")
    public ResponseEntity<Object> addPhone(@Valid @RequestBody AddToCartRequest request,
                                           BindingResult bindingResult) {

        quantityValidator.validate(request, bindingResult);

        ResponseEntity<Object> errors = responseErrorValidator.mapValidationService(bindingResult);
        if(!ObjectUtils.isEmpty(errors)) return errors;

        cartService.addPhone(request.getPhoneId(), request.getQuantity());
        Cart cart = cartService.getCart();

        return ResponseEntity.ok(new CartInfoResponse(cart.getTotalQuantity(), cart.getTotalCost()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    private ResponseEntity<?> invalidFormatRequestHandle() {
        return new ResponseEntity<>(new InvalidRequestException("Invalid value"), HttpStatus.BAD_REQUEST);
    }
}

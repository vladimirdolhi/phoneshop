package com.es.phoneshop.web.controller.pages;

import com.es.core.model.cart.Cart;
import com.es.core.service.CartService;
import com.es.phoneshop.web.dto.CartDto;
import com.es.phoneshop.web.dto.CartItemDto;
import com.es.phoneshop.web.util.validation.UpdateCartDtoValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/cart")
public class CartPageController {
    @Resource
    private CartService cartService;

    @Resource
    private UpdateCartDtoValidator updateCartDtoValidator;

    @RequestMapping(method = RequestMethod.GET)
    public String getCart(Model model) {
        Cart cart = cartService.getCart();
        model.addAttribute("cart", cart);
        if (!model.containsAttribute("cartDto")) {
            model.addAttribute("cartDto", convertToCartDto(cart));
        }
        return "cart";
    }

    @PostMapping("/update")
    public String updateCart(@Valid @ModelAttribute("cartDto") CartDto cartDto, BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {
        List<CartItemDto> items = new ArrayList<>(cartDto.getItems());
        updateCartDtoValidator.validate(cartDto, bindingResult);
        Map<Long, Long> validItems = new HashMap<>(cartDto.getItems().stream()
                .collect(Collectors.toMap(CartItemDto::getPhoneId,
                        CartItemDto::getQuantity)));
        cartService.update(validItems);
        cartDto.setItems(items);
        if(bindingResult.hasErrors()){
            Map<String, String> errors = bindingResult.getFieldErrors().stream()
                    .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
            redirectAttributes.addFlashAttribute("errors", errors);
        }
        redirectAttributes.addFlashAttribute("cartDto", cartDto);
        return "redirect:/cart";
    }

    @PostMapping("/delete/{id}")
    public String updateCart(@PathVariable Long id) {
        cartService.remove(id);
        return "redirect:/cart";
    }

    private CartDto convertToCartDto(Cart cart) {
        return new CartDto(cart.getItems().stream().map(item -> new CartItemDto(item.getPhone().getId(),
                item.getQuantity())).collect(Collectors.toList()));
    }
}

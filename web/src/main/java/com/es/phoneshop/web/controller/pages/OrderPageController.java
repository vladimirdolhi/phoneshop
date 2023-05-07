package com.es.phoneshop.web.controller.pages;

import com.es.core.model.cart.Cart;
import com.es.core.model.order.Order;
import com.es.core.service.CartService;
import com.es.core.service.OrderService;
import com.es.core.exception.OutOfStockException;
import com.es.phoneshop.web.dto.OrderDataDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.UUID;

@Controller
@RequestMapping(value = "/order")
public class OrderPageController {

    @Resource
    private OrderService orderService;

    @Resource
    private CartService cartService;

    @GetMapping
    public String getOrder(Model model) throws OutOfStockException {
        Cart cart = cartService.getCart();
        BigDecimal deliveryPrice = orderService.getDeliveryPrice();
        Long totalCartPrice = cart.getTotalCost().longValue() + deliveryPrice.longValue();

        model.addAttribute("cart", cart);
        model.addAttribute("deliveryPrice", deliveryPrice);
        model.addAttribute("total", totalCartPrice);
        if (!model.containsAttribute("orderDataDto")) {
            model.addAttribute("orderDataDto", new OrderDataDto());
        }
        return "order";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String placeOrder(@Valid @ModelAttribute("orderDataDto") OrderDataDto orderDataDto, BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) throws OutOfStockException {

        if (bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.orderDataDto", bindingResult);
            redirectAttributes.addFlashAttribute("orderDataDto", orderDataDto);
            return "redirect:/order";
        }

        Order order = orderService.createOrder(cartService.getCart());
        enrichOrder(order, orderDataDto);

        orderService.placeOrder(order);

        return "redirect:/orderOverview/" + order.getSecureId();
    }

    @ExceptionHandler(OutOfStockException.class)
    private String outOfStockHandle(OutOfStockException ex, RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("errorMsg", ex.getMessage());

        return "redirect:/order";
    }

    private void enrichOrder(Order order, OrderDataDto orderDataDto){
        order.setSecureId(UUID.randomUUID());
        order.setFirstName(orderDataDto.getFirstName());
        order.setLastName(orderDataDto.getLastName());
        order.setDeliveryAddress(orderDataDto.getAddress());
        order.setContactPhoneNo(orderDataDto.getPhone());
        order.setAdditionalInfo(orderDataDto.getAdditionalInfo());
    }
}

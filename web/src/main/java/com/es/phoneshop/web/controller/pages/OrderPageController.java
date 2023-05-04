package com.es.phoneshop.web.controller.pages;

import com.es.core.model.cart.Cart;
import com.es.core.service.CartService;
import com.es.core.service.OrderService;
import com.es.core.exception.OutOfStockException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/order")
public class OrderPageController {

    @Value("${delivery.price}")
    private Integer deliveryPrice = 5;

    @Resource
    private OrderService orderService;

    @Resource
    private CartService cartService;

    @GetMapping
    public String getOrder(Model model) throws OutOfStockException {
        Cart cart = cartService.getCart();
        Long totalCartPrice = cart.getTotalCost().longValue() + deliveryPrice;

        model.addAttribute("cart", cart);
        model.addAttribute("deliveryPrice", deliveryPrice);
        model.addAttribute("total", totalCartPrice);

        return "order";
    }

    @RequestMapping(method = RequestMethod.POST)
    public void placeOrder() throws OutOfStockException {
        orderService.placeOrder(null);
    }
}

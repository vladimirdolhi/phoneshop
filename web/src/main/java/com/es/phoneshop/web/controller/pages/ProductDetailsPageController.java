package com.es.phoneshop.web.controller.pages;

import com.es.core.service.CartService;
import com.es.core.service.PhoneService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/products")
public class ProductDetailsPageController {

    @Resource
    private PhoneService phoneService;
    @Resource
    private CartService cartService;

    @GetMapping("/{id}")
    public String showProductDetails(@PathVariable("id") long id, Model model) {

        model.addAttribute("cart", cartService.getCart());
        model.addAttribute("phone",phoneService.get(id));
        System.out.println(phoneService.get(id));
        return "productDetails";
    }
}

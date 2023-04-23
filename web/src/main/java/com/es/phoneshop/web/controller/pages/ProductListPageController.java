package com.es.phoneshop.web.controller.pages;

import javax.annotation.Resource;

import com.es.core.model.phone.SortField;
import com.es.core.model.phone.SortOrder;
import com.es.core.service.CartService;
import com.es.core.service.PhoneService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequestMapping (value = "/productList")
@SessionAttributes("cart")
public class ProductListPageController {
    @Resource
    private PhoneService phoneService;
    @Resource
    private CartService cartService;

    @RequestMapping(method = RequestMethod.GET)
    public String showProductList(Model model,
                                  @RequestParam(required = false) String query,
                                  @RequestParam(required = false) SortField sort,
                                  @RequestParam(required = false) SortOrder order) {
        model.addAttribute("phones", phoneService.findAll(query, sort, order, true,0, 100));
        model.addAttribute("cart", cartService.getCart());

        return "productList";
    }
}

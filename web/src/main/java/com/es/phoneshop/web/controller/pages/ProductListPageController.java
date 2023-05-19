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

    private final int PRODUCTS_PER_PAGE = 20;

    @RequestMapping(method = RequestMethod.GET)
    public String showProductList(Model model,
                                  @RequestParam(defaultValue = "1") int pageNumber,
                                  @RequestParam(required = false) String query,
                                  @RequestParam(required = false) SortField sort,
                                  @RequestParam(required = false) SortOrder order) {


        model.addAttribute("phones", phoneService.findAll(query, sort, order, true,
                PRODUCTS_PER_PAGE * (pageNumber - 1), PRODUCTS_PER_PAGE));

        int itemsTotal = phoneService.count(query, sort, order, true, -1, -1);
        int pagesTotal = (int) Math.ceil(itemsTotal / (float) PRODUCTS_PER_PAGE);

        model.addAttribute("itemsTotalCount", itemsTotal);
        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("count", pagesTotal);
        model.addAttribute("cart", cartService.getCart());

        return "productList";
    }
}

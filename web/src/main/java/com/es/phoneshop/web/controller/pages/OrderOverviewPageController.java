package com.es.phoneshop.web.controller.pages;

import com.es.core.model.order.Order;
import com.es.core.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.UUID;

@Controller
@RequestMapping(value = "/orderOverview")
public class OrderOverviewPageController {
    @Resource
    private OrderService orderService;

    @GetMapping("/{id}")
    public String orderOverview(@PathVariable(name = "id") UUID uuid, Model model) {
        Order order = orderService.get(uuid);
        model.addAttribute("order", order);

        return "orderOverview";
    }
}

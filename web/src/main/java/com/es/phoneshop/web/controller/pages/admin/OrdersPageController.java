package com.es.phoneshop.web.controller.pages.admin;

import com.es.core.model.order.OrderStatus;
import com.es.core.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/admin/orders")
public class OrdersPageController {

    @Resource
    private OrderService orderService;

    @GetMapping
    public String getOrders(Model model) {
        model.addAttribute("orders", orderService.findAll());
        return "admin/adminOrders";
    }

    @GetMapping("/{orderNumber}")
    public String getOrderDetails(@PathVariable Long orderNumber, Model model) {
        model.addAttribute("order", orderService.get(orderNumber));
        return "admin/adminOrderDetails";
    }

    @PostMapping("/{orderNumber}")
    public String updateOrderStatus(@PathVariable Long orderNumber,
                                    @RequestParam OrderStatus status) {
        orderService.updateStatus(orderNumber, status);
        return "redirect:/admin/orders/" + orderNumber;
    }
}

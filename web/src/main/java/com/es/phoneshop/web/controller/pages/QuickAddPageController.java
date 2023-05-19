package com.es.phoneshop.web.controller.pages;

import com.es.core.service.CartService;
import com.es.core.service.PhoneService;
import com.es.phoneshop.web.dto.QuickAddDto;
import com.es.phoneshop.web.dto.QuickAddItemDto;
import com.es.phoneshop.web.util.validation.QuickAddDtoValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
@RequestMapping(value = "/quickAdd")
public class QuickAddPageController {
    @Resource
    PhoneService phoneService;
    @Resource
    private CartService cartService;

    @Resource
    private QuickAddDtoValidator quickAddDtoValidator;

    @Value("${quickAdd.rows.count}")
    private Integer quickAddRowsCount;

    @GetMapping
    public String getPage(Model model) {

        model.addAttribute("rowsCount", quickAddRowsCount);

        if (!model.containsAttribute("quickAddDto")) {
            model.addAttribute("quickAddDto", new QuickAddDto());
        }

        return "quickAdd";
    }

    @PostMapping
    public String add(@Valid @ModelAttribute("quickAddDto") QuickAddDto quickAddDto, BindingResult bindingResult,
                      RedirectAttributes redirectAttributes) {

        List<QuickAddItemDto> items = new ArrayList<>(quickAddDto.getItems());

        quickAddDtoValidator.validate(quickAddDto, bindingResult);


        Map<Long, Long> validItems = new HashMap<>(quickAddDto.getItems().stream()
                .collect(Collectors.toMap(
                        item -> phoneService.getByModel(item.getModel()).getId(),
                        item -> Long.parseLong(item.getQuantity())
                )));

        validItems.forEach((key, value) -> cartService.addPhone(key, value));

        String successMsg = deleteValidItemsAndGetMessage(items, quickAddDto.getItems());
        redirectAttributes.addFlashAttribute("successMsg", successMsg);

        if (bindingResult.hasErrors()){

            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.quickAddDto", bindingResult);
            quickAddDto.setItems(items);
            redirectAttributes.addFlashAttribute("quickAddDto", quickAddDto);

        }

        return "redirect:/quickAdd";
    }

    private String deleteValidItemsAndGetMessage(List<QuickAddItemDto> items, List<QuickAddItemDto> validItems) {
        StringBuilder stringBuilder = new StringBuilder();
        items.forEach(item -> {
            validItems.stream()
                    .filter(validItem -> item.getModel().equals(validItem.getModel()))
                    .findFirst()
                    .ifPresent(validItem -> {
                        if (!item.getModel().equals("")) {
                            stringBuilder.append("Successfully added model: " + item.getModel() + "\n");
                        }
                        item.setModel("");
                        item.setQuantity("");
                    });
        });

        return stringBuilder.toString();
    }

}

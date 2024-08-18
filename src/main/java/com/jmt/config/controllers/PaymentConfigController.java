package com.jmt.config.controllers;

import com.jmt.config.service.ConfigInfoService;
import com.jmt.config.service.ConfigSaveService;
import com.jmt.global.exceptions.ExceptionProcessor;
import com.jmt.order.constants.PayMethod;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/config/payment")
public class PaymentConfigController implements ExceptionProcessor, CommonConfig {
    private final ConfigInfoService infoService;
    private final ConfigSaveService saveService;

    @ModelAttribute
    public String subMenuCode() {
        return "payment";
    }

    @ModelAttribute("payMethods")
    public List<String[]> payMethod() {
        return PayMethod.getList();
    }

    @GetMapping
    public String index(Model model) {
        PaymentConfig form = infoService.get(subMenuCode(), PaymentConfig.class).orElseGet(PaymentConfig::new);

        model.addAttribute("paymentConfig", form);
        return "config/payment";
    }

    @PostMapping
    public String save(PaymentConfig form, Model model) {
        System.out.println("==payment save====form===");
        System.out.println(form);
        saveService.save(subMenuCode(), form);
        model.addAttribute("message", "저장되었습니다");

        return "config/payment";
    }
}

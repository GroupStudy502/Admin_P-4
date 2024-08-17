package com.jmt.config.controllers;

import com.jmt.config.service.ConfigInfoService;
import com.jmt.config.service.ConfigSaveService;
import com.jmt.global.exceptions.ExceptionProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/config/api")
@RequiredArgsConstructor
public class ApiConfigController  implements ExceptionProcessor,CommonConfig {

    private final ConfigSaveService saveService;
    private final ConfigInfoService infoService;

    @ModelAttribute("subMenuCode")
    public String getSubMenuCode() {
        return "api";
    }

    @ModelAttribute("pageTitle")
    public String getPageTitle() {
        return "API 설정";
    }

    @GetMapping
    public String index(Model model) {

        ApiConfig config = infoService.get("apiConfig", ApiConfig.class).orElseGet(ApiConfig::new);

        model.addAttribute("apiConfig", config);

        return "config/api";
    }

    @PostMapping
    public String save(ApiConfig config, Model model) {

        saveService.save("apiConfig", config);

        model.addAttribute("message", "저장되었습니다.");

        return "config/api";
    }

}
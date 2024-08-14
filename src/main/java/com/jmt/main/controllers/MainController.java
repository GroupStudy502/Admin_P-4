package com.jmt.main.controllers;

import com.jmt.global.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class MainController {

    private final Utils utils;
    @GetMapping
    public String index() {

        return "redirect:" + utils.redirectUrl("/config");

        //return "config/api";
    }
}

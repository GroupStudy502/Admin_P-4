package com.jmt.config.controllers;

import lombok.Data;

import java.util.List;

@Data
public class PaymentConfig {
    private String mid; //상점아이디
    private String signKey; //사인키
    private List<String> payMethods; //결제수단
}

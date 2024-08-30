package com.jmt.member.controllers;

import lombok.Data;

@Data
public class RequestAuthority {
    private long memberSeq;
    private String authorityName;
    private boolean invoke;
}

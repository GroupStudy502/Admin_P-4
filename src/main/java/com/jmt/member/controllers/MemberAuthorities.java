package com.jmt.member.controllers;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberAuthorities {
    private String memberSeq;
    private String authorityName;
    private String isTrue;

}

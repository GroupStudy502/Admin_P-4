package com.jmt.member.controllers;

import com.jmt.global.rests.JSONData;
import com.jmt.member.services.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberRestController {

    private final MemberService memberService;

    @PostMapping("/authorities/save")
    public JSONData save(RequestAuthority form) {
        return memberService.saveMemberAuthority(form);
    }
}

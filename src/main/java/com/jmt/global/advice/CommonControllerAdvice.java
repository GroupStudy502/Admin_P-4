package com.jmt.global.advice;


import com.jmt.member.MemberUtil;
import com.jmt.member.entities.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@RequiredArgsConstructor
@ControllerAdvice("com.jmt")
public class CommonControllerAdvice {
    private final MemberUtil memberUtil;

    @ModelAttribute("loggedMember")
    public Member loggedMember() {

        return memberUtil.getMember();
    }

    @ModelAttribute("isLogin")
    public boolean isLogin() {

        return memberUtil.isLogin();
    }

    @ModelAttribute("isAdmin")
    public boolean isAdmin() {
        return memberUtil.isAdmin();
    }
}

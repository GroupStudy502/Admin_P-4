package com.jmt.member.controllers;

import com.jmt.global.ListData;
import com.jmt.member.constants.Authority;
import com.jmt.member.entities.Member;
import com.jmt.member.services.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    @GetMapping()
    public String list(MemberSearch search, Model model)  {

        ListData<Member> data = memberService.getList(search);

        model.addAttribute("members", data.getItems());
        model.addAttribute("pagination", data.getPagination());

        List<Authority> allAuthorities = List.of(Authority.ADMIN, Authority.USER, Authority.ALL);
        model.addAttribute("allAuthorities", allAuthorities);
        model.addAttribute("addScript", List.of("member"));


        return "member/list";
    }

    @GetMapping("/delete/{seq}")
    public String delete(@PathVariable("seq") Long seq, MemberSearch search, Model model) {
        Member member = memberService.delete(seq);

        model.addAttribute("script", "parent.location.reload();");
        return "common/_execute_script";
        //return "redirect:/member";
    }
}

package com.jmt.member.controllers;

import com.jmt.member.entities.Authorities;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class MemberAuthorities {
    private Long seq;
    private List<Authorities> authorities;


}

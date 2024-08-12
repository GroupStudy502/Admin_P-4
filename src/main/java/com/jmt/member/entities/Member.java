package com.jmt.member.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jmt.global.entities.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Member extends BaseEntity {
    private Long seq;
    private String email;
    private String password;
    private String userName;
    private String mobile;
    private List<Authorities> authorities;
}
package com.jmt.member.controllers;

import com.jmt.global.CommonSearch;
import lombok.Data;

@Data
public class MemberSearch extends CommonSearch {
    private int limit = 20; // 페이지당 갯수
    private String sort; // 정렬 조건
}

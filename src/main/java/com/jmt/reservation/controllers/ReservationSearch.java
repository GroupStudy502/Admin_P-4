package com.jmt.reservation.controllers;

import com.jmt.global.CommonSearch;
import lombok.Data;

@Data
public class ReservationSearch extends CommonSearch {

    private int limit = 20; // 페이지당 갯수
    private String sort; // 정렬 조건
}

package com.jmt.reservation.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jmt.global.entities.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FoodMenu extends BaseEntity {
    private Long menuId; //메뉴 ID

    private String menuNm; // 메뉴명
    private Integer menuPrice; // 메뉴 가격
    private Boolean spcltMenuYn; // 지역 특산메뉴여부
    private String spcltMenuNm; // 지역 특산메뉴명
    private String spcltMenuOgnUrl; // 지역 특산메뉴 URL
    private String menuDscrn; // 메뉴설명(주재료, 조리법 ,소스, 옵션)
    private String menuCtgryLclasNm; // 메뉴 카테고리 대분류명
    private String menuCtgrySclasNm; // 메뉴 카테고리 소분류

    @JsonIgnore
    private Restaurant restaurant;

    @ToString.Exclude
    private List<FoodMenuImage> images;

}


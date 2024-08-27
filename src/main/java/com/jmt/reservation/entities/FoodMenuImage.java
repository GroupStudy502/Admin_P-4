package com.jmt.reservation.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FoodMenuImage {

    private Long seq;
    private FoodMenu foodMenu;
    private String foodImgUrl;
}

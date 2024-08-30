package com.jmt.reservation.controllers;

import com.jmt.reservation.entities.Reservation;
import com.jmt.reservation.services.ReservationInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/reservation2")
public class ReservationController2 {
    private final ReservationInfoService infoService;

    @GetMapping()
    public String list(ReservationSearch search, Model model)  {

        List<Reservation> data = infoService.getList2(search);
        model.addAttribute("items", data);

        return "reservation/list2";
    }

}

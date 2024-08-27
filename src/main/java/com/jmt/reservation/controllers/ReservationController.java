package com.jmt.reservation.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
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
@RequestMapping("/reservation")
public class ReservationController {
    private final ReservationInfoService infoService;

    @GetMapping()
    public String list(Model model) throws JsonProcessingException {

        List<Reservation> items = infoService.getList();
        model.addAttribute("items", items);
        //model.addAttribute("pagination", data.getPagination());

        return "reservation/list";
    }

}

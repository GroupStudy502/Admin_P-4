package com.jmt.reservation.controllers;

import com.jmt.global.ListData;
import com.jmt.global.Utils;
import com.jmt.reservation.entities.Reservation;
import com.jmt.reservation.services.ReservationInfoService;
import com.jmt.reservation.services.ReservationUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Objects;

@Controller
@RequiredArgsConstructor
@RequestMapping("/reservation")
public class ReservationController {
    private final ReservationInfoService infoService;
    private final ReservationUpdateService updateService;
    private final Utils utils;


    @GetMapping()
    public String list(@ModelAttribute  ReservationSearch search, Model model)  {

        //if(search.getLimit() == 0) search.setLimit(20); //디폴트 limit
        ListData<Reservation> data = infoService.getList(search);
        data.getItems().forEach(item-> System.out.println(item.getClass()));
        model.addAttribute("items", data.getItems());
        model.addAttribute("pagination", data.getPagination());

        return "reservation/list";
    }

    @GetMapping("/delete/{orderNo}")
    public String deletePost(@PathVariable("orderNo") Long orderNo, ReservationSearch search, Model model ) {

        Reservation reservation = updateService.deleteReservation(orderNo);

        if(reservation.getOrderNo() != null && Objects.equals(reservation.getOrderNo(), orderNo)) {
            model.addAttribute("message", "삭제 성공");
        } else {
            model.addAttribute("message", "삭제 실패");
        }

        model.addAttribute("script", "parent.location.reload();");
        return "common/_execute_script";
       // return "redirect:" + utils.redirectUrl("/reservation" );
    }
}

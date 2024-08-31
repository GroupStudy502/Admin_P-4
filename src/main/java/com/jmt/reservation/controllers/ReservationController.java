package com.jmt.reservation.controllers;

import com.jmt.global.ListData;
import com.jmt.global.Utils;
import com.jmt.reservation.constants.ReservationStatus;
import com.jmt.reservation.entities.Reservation;
import com.jmt.reservation.services.ReservationInfoService;
import com.jmt.reservation.services.ReservationUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/reservation")
public class ReservationController {
    private final ReservationInfoService infoService;
    private final ReservationUpdateService updateService;
    private final Utils utils;

    //@ModelAttribute("statuses")
    /*public List<String[]> getStatus() {
        return ReservationStatus.getList();
    }
    */
    @ModelAttribute("statuses")
    public ReservationStatus[] getStatuses()
    {
        return ReservationStatus.values();
    }


    @GetMapping()
    public String list(@ModelAttribute  ReservationSearch search, Model model)  {

        ListData<Reservation> data = infoService.getList(search);

        model.addAttribute("addScript", List.of("reservation"));

        model.addAttribute("items", data.getItems());
        model.addAttribute("pagination", data.getPagination());


        //List<String[]> statuses = getStatus();
        /*
        for(int i = 0 ; i < statuses.size(); i ++) {
            for(int j = 0 ; j < statuses.get(i).length; j ++ ) {
                System.out.println(statuses.get(i)[j]);
            }
        }

        for( ReservationStatus status : ReservationStatus.values() ) {
            System.out.println(status.name() + "=>" +  status.getTitle());
        }
        */

        return "reservation/list";
    }

    @GetMapping("/delete/{orderNo}")
    public String deletePost(@PathVariable("orderNo") Long orderNo, ReservationSearch search, Model model ) {

        updateService.deleteReservation(orderNo);

        model.addAttribute("script", "parent.location.reload();");
        return "common/_execute_script";
       // return "redirect:" + utils.redirectUrl("/reservation" );
    }
}

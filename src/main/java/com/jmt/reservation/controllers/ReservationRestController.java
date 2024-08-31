package com.jmt.reservation.controllers;

import com.jmt.global.rests.JSONData;
import com.jmt.reservation.services.ReservationUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservation")
@RequiredArgsConstructor
public class ReservationRestController {
    private final ReservationUpdateService service;

    @PostMapping("/status/update")
    public JSONData save(RequestReservationStatus form) {

        return service.updateReservation(form);
    }
}

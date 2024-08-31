package com.jmt.reservation.controllers;

import com.jmt.reservation.constants.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestReservationStatus {
    private Long orderNo;
    private ReservationStatus status;
}

package com.jmt.reservation.constants;

import java.util.List;

public enum ReservationStatus {
    START("접수중"),
    APPLY("예약접수"), // 예약 접수
    CONFIRM("예약확인"),
    CANCEL("접수취소"),
    REFUND("예약취소");

    private final String title;

    ReservationStatus(String title) {
        this.title = title;
    }

    public static List<String[]> getList() {
        return List.of(
                new String[] {START.name(), START.title},
                new String[] {APPLY.name(), APPLY.title},
                new String[] {CONFIRM.name(), CONFIRM.title},
                new String[] {CANCEL.name(), CANCEL.title},
                new String[] {REFUND.name(), REFUND.title}
        );
    }
}
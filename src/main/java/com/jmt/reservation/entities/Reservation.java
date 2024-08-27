package com.jmt.reservation.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jmt.global.entities.BaseEntity;
import com.jmt.member.entities.Member;
import com.jmt.order.constants.PayMethod;
import com.jmt.reservation.constants.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Reservation extends BaseEntity {
    private Long orderNo = System.currentTimeMillis(); // 예약 접수 번호
    private ReservationStatus status;
    private Member member;
    private Restaurant restaurant;
    private String name; // 예약자명
    private String email; // 예약자 이메일
    private String mobile; // 예약자 휴대전화번호
    private String rName; // 식당명
    private String rAddress; // 식당 주소
    private String rTel; // 식당 연락처
    private LocalDateTime rDateTime; // 예약 일시
    private int price; // 1명당 예약금
    private int persons; // 예약 인원수
    private PayMethod payMethod;
    private String payLog; // 결제 로그
    private String payTid; // PG 거래 ID(tid)
    private String payBankName; // 가상계좌 은행
    private String payBankAccount; // 가상계좌
    private int totalPayPrice; // 총 결제금액
}
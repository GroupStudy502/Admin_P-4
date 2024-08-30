package com.jmt.reservation.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jmt.global.Utils;
import com.jmt.global.rests.JSONData;
import com.jmt.reservation.entities.Reservation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReservationUpdateService {
    private final Utils utils;
    private final RestTemplate restTemplate;
    private final ObjectMapper om;


    public Reservation deleteReservation(Long seq) {
        // api - /board/admin/delete/seq
        String url = utils.url("/reservation/admin/delete/" + seq, "api-service");
        Map<String, Long> params = new HashMap<>();
        params.put("seq", seq);

        try {
            String jsonBody = om.writeValueAsString(params);
            HttpHeaders headers = utils.getCommonHeaders("DELETE");
            HttpEntity<String> request = new HttpEntity<>(jsonBody, headers);
            ResponseEntity<JSONData> response = restTemplate.exchange(URI.create(url), HttpMethod.DELETE, request, JSONData.class);
            System.out.println("response : " + response);
            if (!response.getStatusCode().is2xxSuccessful() || !response.getBody().isSuccess()) {
                return null;
            }
            return om.readValue(om.writeValueAsString(response.getBody().getData()), Reservation.class);

        } catch (RuntimeException | JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}


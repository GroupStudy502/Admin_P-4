package com.jmt.reservation.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jmt.global.Utils;
import com.jmt.global.exceptions.UnAuthorizedException;
import com.jmt.global.rests.JSONData;
import com.jmt.reservation.entities.Reservation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationInfoService {
    private final Utils utils;
    private final RestTemplate restTemplate;
    private final ObjectMapper om;

    public List<Reservation> getList() throws JsonProcessingException {
        String url = utils.url("/reservation/admin/list", "api-service");

        String token = utils.getToken();
        if (!StringUtils.hasText(token)) {
            throw new UnAuthorizedException();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<JSONData> request = new HttpEntity<>(headers);

        System.out.println("url:" + url );

        ResponseEntity<JSONData> response = restTemplate.exchange(URI.create(url), HttpMethod.GET, request, JSONData.class);

        if(!response.getStatusCode().toString().contains("200")) {
            throw new RuntimeException("api Server returned: " + response.getStatusCode());
        }
        String jsonString = om.writeValueAsString(response.getBody().getData());
        System.out.println("jsonString :" + jsonString);

        List<Reservation> items = om.readValue(jsonString, new TypeReference<>() {});
        System.out.println(items);

        return items;
    }
}

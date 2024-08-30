package com.jmt.reservation.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jmt.global.ListData;
import com.jmt.global.Utils;
import com.jmt.global.rests.JSONData;
import com.jmt.reservation.controllers.ReservationSearch;
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
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ReservationInfoService {
    private final Utils utils;
    private final RestTemplate restTemplate;
    private final ObjectMapper om;

    public ListData<Reservation> getList(ReservationSearch search) {
        String url = utils.url("/reservation/admin/list", "api-service");

        HttpHeaders headers = utils.getCommonHeaders("GET");
        HttpEntity<Void> request = new HttpEntity<>(headers);

        int page = search.getPage();
        int limit = search.getLimit();
        String sopt = Objects.requireNonNullElse(search.getSopt(), "");
        String skey = Objects.requireNonNullElse(search.getSkey(), "");
        String sort = Objects.requireNonNullElse(search.getSort(), "");

        if(StringUtils.hasText(skey)) {
            skey = URLEncoder.encode(skey, StandardCharsets.UTF_8);
        }
        url = url + String.format("?sopt=%s&skey=%s&page=%d&limit=%d&sort=%s", sopt, skey, page,limit,sort);

        ResponseEntity<JSONData> response = restTemplate.exchange(URI.create(url), HttpMethod.GET, request, JSONData.class);

        Object data = response.getBody().getData();
        try {
            return om.readValue(om.writeValueAsString(data), new TypeReference<ListData<Reservation>>() {});

        } catch(Exception e) {
            e.printStackTrace();
        }

        return new ListData<>();

    }
    public List<Reservation> getList2(ReservationSearch search) {
        String url = utils.url("/reservation/admin/list2", "api-service");

        HttpHeaders headers = utils.getCommonHeaders("GET");
        HttpEntity<Void> request = new HttpEntity<>(headers);

        int page = search.getPage();
        int limit = search.getLimit();
        String sopt = Objects.requireNonNullElse(search.getSopt(), "");
        String skey = Objects.requireNonNullElse(search.getSkey(), "");
        String sort = Objects.requireNonNullElse(search.getSort(), "");

        if(StringUtils.hasText(skey)) {
            skey = URLEncoder.encode(skey, StandardCharsets.UTF_8);
        }
        url = url + String.format("?sopt=%s&skey=%s&page=%d&limit=%d&sort=%s", sopt, skey, page,limit,sort);

        ResponseEntity<JSONData> response = restTemplate.exchange(URI.create(url), HttpMethod.GET, request, JSONData.class);

        if(!response.getStatusCode().is2xxSuccessful() || !response.getBody().isSuccess()) {
            return null;
        }
        Object data = response.getBody().getData();
        System.out.println(data);
        try {
            return om.readValue(om.writeValueAsString(data), List.class);

        } catch(Exception e) {
            e.printStackTrace();
        }


        return null;
    }

}

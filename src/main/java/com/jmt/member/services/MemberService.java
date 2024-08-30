package com.jmt.member.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jmt.global.Utils;
import com.jmt.global.rests.JSONData;
import com.jmt.member.controllers.RequestAuthority;
import com.jmt.member.entities.Member;
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
public class MemberService {

    private final Utils utils;
    private final RestTemplate restTemplate;
    private final ObjectMapper om;

    public Member delete(Long seq)  {
        String url = utils.url("/member/delete/" + seq, "api-service");
        Map<String, Long> params = new HashMap<>();
        params.put("seq", seq);

        try {
            String jsonBody = om.writeValueAsString(params);
            HttpHeaders headers = utils.getCommonHeaders("DELETE");

            HttpEntity<String> request = new HttpEntity<>(jsonBody, headers);
            ResponseEntity<JSONData> response = restTemplate.exchange(URI.create(url), HttpMethod.DELETE, request, JSONData.class);
            if (!response.getStatusCode().is2xxSuccessful() || !response.getBody().isSuccess()) {
                return null;
            }
            Member member = om.readValue(om.writeValueAsString(response.getBody().getData()), Member.class);
        }catch(RuntimeException | JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public RequestAuthority saveMemberAuthority(RequestAuthority form) {
        String url = utils.url("/member/authorities/save" , "api-service");
        Map<String, RequestAuthority> params = new HashMap<>();
        params.put("requestAuthority", form);

        HttpHeaders headers = utils.getCommonHeaders("POST");
        try {
            String jsonBody = om.writeValueAsString(params);
            HttpEntity<String> request = new HttpEntity<>(jsonBody, headers);
            ResponseEntity<String> response = restTemplate.exchange(URI.create(url), HttpMethod.POST, request, String.class);
            return om.readValue(om.writeValueAsString(response.getBody()), RequestAuthority.class);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}

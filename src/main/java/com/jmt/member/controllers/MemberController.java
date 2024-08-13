package com.jmt.member.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jmt.global.rests.JSONData;
import com.jmt.member.entities.Member;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final DiscoveryClient discoveryClient;
    private final RestTemplate restTemplate;
    private final ObjectMapper om;

    @GetMapping()
    public String list(HttpServletRequest req) throws JsonProcessingException {
        List<ServiceInstance> instances = discoveryClient.getInstances("api-service");
        if (instances.isEmpty()) {
            throw new RuntimeException("No Discovery instances found");
        }
        System.out.println(instances.get(0).getUri().toString());
        String url = instances.get(0).getUri().toString();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyMDFAdGVzdC5jb20iLCJleHAiOjE3MjM1NDI2Njl9.SsIu8Rsft8dRI4zN2bAf1BMHsGVjJiTLPeeKJxC9NUNBJZnh6j7k5ZrQzreV5VraosiTvtnS-KA6vjCbZxVcPg");

        HttpEntity<JSONData> request = new HttpEntity<>(headers);

        url = url + "/account/list";
        ResponseEntity<JSONData> response = restTemplate.exchange(URI.create(url), HttpMethod.GET, request, JSONData.class);

        if(!response.getStatusCode().toString().contains("200")) {
            throw new RuntimeException("api Server returned: " + response.getStatusCode());
        }
        String jsonString = om.writeValueAsString(response.getBody().getData());
        System.out.println("jsonString :" + jsonString);

        List<Member> members = om.readValue(jsonString, new TypeReference<List<Member>>() {});
        System.out.println(members);

        req.setAttribute("members", members);
        return "member/list";
    }

}

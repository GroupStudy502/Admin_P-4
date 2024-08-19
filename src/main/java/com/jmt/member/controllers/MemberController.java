package com.jmt.member.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jmt.global.Utils;
import com.jmt.global.exceptions.UnAuthorizedException;
import com.jmt.global.rests.JSONData;
import com.jmt.member.constants.Authority;
import com.jmt.member.entities.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
    private final Utils utils;

    @GetMapping()
    public String list(Model model) throws JsonProcessingException {

        List<ServiceInstance> instances = discoveryClient.getInstances("api-service");
        if (instances.isEmpty()) {
            throw new RuntimeException("No Discovery instances found");
        }

        String token = utils.getToken();
        if (!StringUtils.hasText(token)) {
            throw new UnAuthorizedException();
        }

        System.out.println(instances.get(0).getUri().toString());
        String url = instances.get(0).getUri().toString();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<JSONData> request = new HttpEntity<>(headers);

        url = url + "/account/list";
        ResponseEntity<JSONData> response = restTemplate.exchange(URI.create(url), HttpMethod.GET, request, JSONData.class);

        if(!response.getStatusCode().toString().contains("200")) {
            throw new RuntimeException("api Server returned: " + response.getStatusCode());
        }
        String jsonString = om.writeValueAsString(response.getBody().getData());

        List<Member> members = om.readValue(jsonString, new TypeReference<List<Member>>() {});
        model.addAttribute("members", members);

        List<Authority> allAuthorities = List.of(Authority.ADMIN, Authority.USER, Authority.ALL);
        model.addAttribute("allAuthorities", allAuthorities);
        model.addAttribute("addScript", List.of("member"));

        return "member/list";
    }

    @PostMapping("/authorities/save")
    public String update(MemberAuthorities form, Model model) {


        return "member/list";
    }

}

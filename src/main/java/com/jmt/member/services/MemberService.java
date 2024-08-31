package com.jmt.member.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jmt.global.ListData;
import com.jmt.global.Utils;
import com.jmt.global.rests.JSONData;
import com.jmt.member.controllers.MemberSearch;
import com.jmt.member.controllers.RequestAuthority;
import com.jmt.member.entities.Member;
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
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final Utils utils;
    private final RestTemplate restTemplate;
    private final ObjectMapper om;

    public ListData<Member> getList(MemberSearch search) {

        HttpHeaders headers = utils.getCommonHeaders("GET");
        HttpEntity<JSONData> request = new HttpEntity<>(headers);
        String url = utils.url("/member/list", "api-service");

        int page = search.getPage();
        int limit = search.getLimit();
        String sopt = Objects.requireNonNullElse(search.getSopt(), "");
        String skey = Objects.requireNonNullElse(search.getSkey(), "");

        if(StringUtils.hasText(skey)) {
            skey = URLEncoder.encode(skey, StandardCharsets.UTF_8);
        }

        String sort = Objects.requireNonNullElse(search.getSort(), "");
        url += String.format("?page=%d&limit=%d&sopt=%s&skey=%s&sort=%s", page, limit, sopt, skey, sort);

        ResponseEntity<JSONData> response = restTemplate.exchange(URI.create(url), HttpMethod.GET, request, JSONData.class);

        if (!response.getStatusCode().is2xxSuccessful() || !response.getBody().isSuccess()) {
            return new ListData<>();
        }
        try {

            return om.readValue(om.writeValueAsString(response.getBody().getData()), new TypeReference<ListData<Member>>() {});
        }catch(JsonProcessingException e) {
            e.printStackTrace();
        }
        return new ListData<>();
    }
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

    public JSONData saveMemberAuthority(RequestAuthority form) {
        String url = utils.url("/member/authorities/save" , "api-service");

        HttpHeaders headers = utils.getCommonHeaders("POST");
        try {
            String jsonBody = om.writeValueAsString(form);
            HttpEntity<String> request = new HttpEntity<>(jsonBody, headers);

            // response.getBody() 의 Type : JSONData
            ResponseEntity<JSONData> response = restTemplate.exchange(URI.create(url), HttpMethod.POST, request, JSONData.class);

            System.out.println("(response)" + response + "response.getBody() : " + response.getBody());
            //System.out.println("response.getBody().getData() : " + response.getBody().getData());

            return response.getBody();

        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}

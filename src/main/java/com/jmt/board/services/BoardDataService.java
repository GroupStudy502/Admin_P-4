package com.jmt.board.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jmt.board.entities.BoardData;
import com.jmt.global.Utils;
import com.jmt.global.exceptions.BadRequestException;
import com.jmt.global.rests.JSONData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BoardDataService {
    private final Utils utils;
    private final ObjectMapper om;
    private final RestTemplate restTemplate;

    public BoardData deleteBoardData(Long seq, String mode) {
        // api - /board/admin/delete/seq
        // api - /board/admin/complete/seq
        if(!StringUtils.hasText(mode)) {
           throw new BadRequestException("mode cannot be empty");
        }
        String url;
        if(mode.equals("complete")) {
            url = utils.url("/board/admin/complete/" + seq, "api-service");
        }else {
            url = utils.url("/board/admin/delete/" + seq, "api-service");
        }
        Map<String, Long> params = new HashMap<>();
        params.put("seq", seq);

        try {
            String jsonBody = om.writeValueAsString(params);
            HttpHeaders headers = utils.getCommonHeaders("DELETE");
            HttpEntity<String> request = new HttpEntity<>(jsonBody, headers);
            ResponseEntity<JSONData> response = restTemplate.exchange(URI.create(url), HttpMethod.DELETE, request, JSONData.class);
            System.out.println("response : " + response);
            if(!response.getStatusCode().is2xxSuccessful() || !response.getBody().isSuccess()) {
                return null;
            }
            return om.readValue(om.writeValueAsString(response.getBody().getData()), BoardData.class);

        } catch (RuntimeException | JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}

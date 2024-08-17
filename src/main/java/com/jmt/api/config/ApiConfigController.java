package com.jmt.api.config;

import com.jmt.config.controllers.ApiConfig;
import com.jmt.config.controllers.BasicConfig;
import com.jmt.config.service.ConfigInfoService;
import com.jmt.global.exceptions.RestExceptionProcessor;
import com.jmt.global.exceptions.UnAuthorizedException;
import com.jmt.global.rests.JSONData;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("apiConfigRestController")
@RequestMapping("/api/config")
@RequiredArgsConstructor
public class ApiConfigController implements RestExceptionProcessor {

    private final ConfigInfoService infoService;
    @Value("$secretKey")
    private String secretKey;

    private final PasswordEncoder encoder;
    private final HttpServletRequest request;

    @GetMapping
    public ResponseEntity<JSONData> siteConfig() {
        checkToken();
        BasicConfig config = infoService.get("basic", BasicConfig.class).orElse(null);
        JSONData data = new JSONData();
        data.setSuccess(config != null);
        if(config == null) {
            data.setStatus(HttpStatus.NOT_FOUND);
        }
        data.setData(config);
        return ResponseEntity.status(data.getStatus()).body(data);
    }

    @GetMapping("/apikeys")
    public ResponseEntity<JSONData> apiKeys() {
        checkToken();

        ApiConfig config = infoService.get("apiConfig", ApiConfig.class).orElse(null);

        JSONData data = new JSONData();
        data.setSuccess(config != null);
        if (config == null) {
            data.setStatus(HttpStatus.NOT_FOUND);
        }
        data.setData(config);

        return ResponseEntity.status(data.getStatus()).body(data);
    }

    private void checkToken() {
        /**
         * 요청헤더
         * Authorization : Bearer Bcrypt 해시
         */
        String bearerToken = request.getHeader("Authorization");
        if(bearerToken == null || !StringUtils.hasText(bearerToken.trim())) {
            throw new UnAuthorizedException();

        }
        String token = bearerToken.substring(7);
        if(encoder.matches(secretKey, token)) {
            throw new UnAuthorizedException();
        }
    }


}

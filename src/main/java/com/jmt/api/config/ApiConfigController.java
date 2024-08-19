package com.jmt.api.config;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("apiConfigRestController")
@RequestMapping("/api/config")
@RequiredArgsConstructor
public class ApiConfigController implements RestExceptionProcessor {

    @Value("$secretKey")
    private String secretKey;

    private final PasswordEncoder encoder;
    private final ConfigInfoService infoService;
    private final HttpServletRequest request;

    @GetMapping({"/{mode}", "/"})
    public ResponseEntity<JSONData> config(@PathVariable(value = "mode", required = false) String mode) {
        checkToken();
        mode = StringUtils.hasText(mode) ? mode : "basic";

        if (mode.equals("apikeys")) mode = "apiConfig";

        Object config = infoService.get(mode, Object.class).orElse(null);

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

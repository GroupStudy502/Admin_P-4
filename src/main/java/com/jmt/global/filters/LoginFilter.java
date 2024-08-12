package com.jmt.global.filters;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.jmt.global.rests.JSONData;
import com.jmt.member.MemberInfo;
import com.jmt.member.constants.Authority;
import com.jmt.member.entities.Authorities;
import com.jmt.member.entities.JwtToken;
import com.jmt.member.entities.Member;
import com.jmt.member.repository.JwtTokenRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class LoginFilter extends GenericFilterBean {

    private final DiscoveryClient discoveryClient;
    private final RestTemplate restTemplate;
    private final ObjectMapper om;
    private final JwtTokenRepository repository;
    private final HttpSession session;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        String token = getToken(request);
        if (StringUtils.hasText(token)) {
            loginProcess(token);
        }

        chain.doFilter(request, response);
    }

    /**
     * JWT 토큰 으로 회원 정보 로그인 처리
     *
     * @param token
     */
    private void loginProcess(String token) {
        JwtToken jwtToken = new JwtToken();
        jwtToken.setSessionId(session.getId());
        jwtToken.setToken(token);
        repository.save(jwtToken);

        List<ServiceInstance> instances = discoveryClient.getInstances("api-service");

        try {
            String apiUrl = String.format("%s%s", instances.get(0).getUri().toString(), "/account");
            // api서버 주소/account

            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(token); // headers.set("Authorization", "Bearer " + token);
            HttpEntity<Void> entity = new HttpEntity<>(headers);
            ResponseEntity<JSONData> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, JSONData.class);

            //System.out.println("2. lgoinprocess()---response: ");
            //System.out.println(response);
            // 성공 응답인 경우 로그인 처리
            if (response.getStatusCode().is2xxSuccessful()) {
                JSONData data = response.getBody();
                if (data != null && data.isSuccess()) {
                    String json = om.writeValueAsString(data.getData());
                    Member member = om.readValue(json, Member.class);
                    List<Authorities> tmp = member.getAuthorities();
                    if (tmp == null || tmp.isEmpty()) {
                        tmp = List.of(Authorities.builder().authority(Authority.USER).build());
                    }

                    List<SimpleGrantedAuthority> authorities = tmp.stream()
                            .map(a -> new SimpleGrantedAuthority(a.getAuthority().name()))
                            .toList();

                    MemberInfo memberInfo = MemberInfo.builder()
                            .email(member.getEmail())
                            .password(member.getPassword())
                            .member(member)
                            .authorities(authorities)
                            .build();

                    Authentication authentication = new UsernamePasswordAuthenticationToken(memberInfo, token, memberInfo.getAuthorities());
                    // 로그인 처리
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }


        } catch (Exception e) {
            // 만료된 경우 또는 인증 오류시 레디스 저장 정보 삭제
            repository.deleteById(session.getId());
        }
    }

    /**
     *  요청 헤더에서 JWT 토큰 추출
     *  Authorization: Bearer JWT토큰 또는 요청 파라미터 token 값
     *
     * @param request
     * @return
     */
    private String getToken(ServletRequest request) {
        HttpServletRequest req = (HttpServletRequest) request;
        String bearerToken = req.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken)
                && bearerToken.toUpperCase().startsWith("BEARER ")) {
            //System.out.println("1---Loginfilter --- getToken: Authorization header- bearerToken: ");
            return bearerToken.substring(7).trim();
        }

        String token = req.getParameter("token");
        if (StringUtils.hasText(token)) {
            //System.out.println("1---Loginfilter --- getToken: req.getParameter(\"token\"): ");
            return token;
        }

        // 토큰이 유입된것이 아니라면 redis 저장소에서 추출

        String id = session.getId();
        JwtToken jwtToken = repository.findById(id).orElse(null);
        if (jwtToken != null) {
            //System.out.println("1---Loginfilter --- getToken: session -> redis : ");
            return jwtToken.getToken();
        }

        return null;
    }
}

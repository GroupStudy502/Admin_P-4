package com.jmt.global;

import com.jmt.global.exceptions.UnAuthorizedException;
import com.jmt.member.entities.JwtToken;
import com.jmt.member.repository.JwtTokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class Utils {
    private final DiscoveryClient discoveryClient;
    private final MessageSource messageSource;
    private final HttpServletRequest request;
    private final JwtTokenRepository jwtTokenRepository;
    private final HttpSession session;

    public HttpHeaders getCommonHeaders(String method) {
        JwtToken jwtToken =  jwtTokenRepository.findById(session.getId()).orElseThrow(UnAuthorizedException::new);
        //JwtToken jwtToken = new JwtToken();
        //jwtToken.setToken("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyMDFAdGVzdC5jb20iLCJleHAiOjE3MjQ2NjMzMjh9.iJH7h9K4kj93P_Hu819LE6ohB4fR4_73li0z3IWeh-PhXwJA1H4u7ugOE1soFYcAFHXLZL90wKaXkVVafCBszQ");

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jwtToken.getToken());
        if (!List.of("GET", "DELETE").contains(method)) {  //GET, DELETE 외응 모두 body 있다
            headers.setContentType(MediaType.APPLICATION_JSON);
        }

        return headers;
    }

    public String getToken() {
        JwtToken jwtToken = jwtTokenRepository.findById(request.getSession().getId()).orElse(null);

        return jwtToken == null ? null : jwtToken.getToken();
    }

    public String url(String url) {
        List<ServiceInstance> instances = discoveryClient.getInstances("admin-service");
        if (instances.isEmpty()) {
            System.out.println("No services found - 인스턴스 없음 - discoveryClient.getInstances(\"admin-service\") ");
            return url;
        }
        System.out.println("---discoveryClient instances ---");
        System.out.println(instances);
        System.out.println("-------------------------");
        instances.forEach(i -> {
            System.out.println("i.getServiceId(): " + i.getServiceId());
            System.out.println("i.getInstanceId(): " + i.getInstanceId());
            System.out.println("i.getHost(): " + i.getHost());
            System.out.println("i.getPort(): " + i.getPort());
            System.out.println("i.getUri(): " + i.getUri());
            System.out.println("i.getMetadata(): " + i.getMetadata());

        });
        return String.format("%s%s", instances.get(0).getUri().toString(), url);
    }
    public String url(String url, String serviceId) {
        List<ServiceInstance> instances = discoveryClient.getInstances(serviceId);

        try {
            return String.format("%s%s", instances.get(0).getUri().toString(), url);
        } catch (Exception e) {
            return String.format("%s://%s:%d%s%s", request.getScheme(), request.getServerName(), request.getServerPort(), request.getContextPath(), url);
        }
    }
    public String redirectUrl(String url) {
        String _fromGateway = Objects.requireNonNullElse(request.getHeader("from-gateway"), "false");
        String gatewayHost = Objects.requireNonNullElse(request.getHeader("gateway-host"), "");
        boolean fromGateway = _fromGateway.equals("true");

        return fromGateway ? request.getScheme() + "://" + gatewayHost + "/admin" + url : request.getContextPath() + url;
    }

    public Map<String, List<String>> getErrorMessages(Errors errors) {//JSON 받을 때는 에러를 직접 가공
        // FieldErrors


        Map<String, List<String>> messages = errors.getFieldErrors()
                .stream()
                .collect(Collectors.toMap(FieldError::getField, e -> getCodeMessages(e.getCodes())));

        // GlobalErrors
        List<String> gMessages = errors.getGlobalErrors()
                .stream()
                .flatMap(e -> getCodeMessages(e.getCodes()).stream()).toList();

        if (!gMessages.isEmpty()) {
            messages.put("global", gMessages);
        }
        return messages;
    }


    public List<String> getCodeMessages(String[] codes) {
        ResourceBundleMessageSource ms = (ResourceBundleMessageSource) messageSource;
        ms.setUseCodeAsDefaultMessage(false);

        List<String> messages = Arrays.stream(codes)
                .map(c -> {
                    try {
                        return ms.getMessage(c, null, request.getLocale());
                    } catch (Exception e) {
                        return "";
                    }
                })
                .filter(s -> !s.isBlank())
                .toList();

        ms.setUseCodeAsDefaultMessage(true);
        return messages;
    }
    public String getMessage(String code){
        List<String> messages = getCodeMessages(new String[]{code});

        return messages.isEmpty() ? code : messages.get(0);
    }

    /**
     * 요청 데이터 단일 조회 편의 함수
     *
     * @param name
     * @return
     */
    public String getParam(String name) {
        return request.getParameter(name);
    }

    /**
     * 요청 데이터 복수개 조회 편의 함수
     *
     * @param name
     * @return
     */
    public String[] getParams(String name) {
        return request.getParameterValues(name);
    }
}
package com.jmt.global.configs;

import com.jmt.global.Utils;
import com.jmt.global.filters.LoginFilter;
import com.jmt.member.repository.JwtTokenRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final LoginFilter loginFilter;
    private final JwtTokenRepository jwtTokenRepository;
    private final Utils utils;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.addFilterBefore(loginFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(c -> {
                    c.requestMatchers("/js/**", "/css/**", "/images/**", "/logout", "/api/board/config/**", "api/config/**").permitAll()
                            .anyRequest().hasAnyAuthority("ADMIN");
                });

        http.logout(c -> {
            c.logoutUrl("/logout")
                    .logoutSuccessHandler((req, res, a) -> {
                        HttpSession session = req.getSession();
                        jwtTokenRepository.deleteById(session.getId());

                        res.sendRedirect(utils.redirectUrl("/"));
                    });
        });

        http.headers(h -> h.frameOptions(f -> f.sameOrigin()));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
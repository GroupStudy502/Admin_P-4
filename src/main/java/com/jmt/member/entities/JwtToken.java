package com.jmt.member.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Data
@RedisHash(timeToLive = 3600L)
public class JwtToken implements Serializable {
    @Id  // Spring Data redis import
    private String sessionId; // 세션 ID
    private String token; // JWT 토큰
}

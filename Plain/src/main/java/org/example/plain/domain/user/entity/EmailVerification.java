package org.example.plain.domain.user.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@AllArgsConstructor
@RedisHash(value = "emailVerification", timeToLive = 600)
public class EmailVerification {
    @Id
    private String email;
    private String code;
} 
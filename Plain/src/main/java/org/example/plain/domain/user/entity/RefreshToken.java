package org.example.plain.domain.user.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@AllArgsConstructor
@RedisHash(value = "refreshToken",timeToLive = 172800)
public class RefreshToken {
    @Id
    private String refreshToken;
    private String userId;
}


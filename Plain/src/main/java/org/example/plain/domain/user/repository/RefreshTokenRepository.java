package org.example.plain.domain.user.repository;

import io.lettuce.core.support.caching.RedisCache;
import org.example.plain.domain.user.entity.RefreshToken;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends RedisCache<String, RefreshToken> {
}

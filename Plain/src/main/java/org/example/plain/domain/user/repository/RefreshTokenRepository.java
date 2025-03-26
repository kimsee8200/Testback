package org.example.plain.domain.user.repository;

import io.lettuce.core.support.caching.RedisCache;
import org.example.plain.domain.user.entity.RefreshToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshToken,String> {
}

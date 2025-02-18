package org.example.plain.domain.user.repository;

import org.example.plain.domain.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
    public Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByEmail(String email);
}

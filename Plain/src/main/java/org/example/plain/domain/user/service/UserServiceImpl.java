package org.example.plain.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.example.plain.common.enums.Role;
import org.example.plain.domain.user.dto.User;
import org.example.plain.domain.user.entity.UserEntity;
import org.example.plain.domain.user.interfaces.UserService;
import org.example.plain.domain.user.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public boolean createUser(User user) {
        UserEntity userEntity = new UserEntity(user);
        userEntity.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userEntity.setRole(Role.NORMAL);
        Objects.requireNonNull(userEntity);
        userRepository.save(userEntity);
        return true;
    }

    @Override
    public boolean updateUser(User user) {
        userRepository.findById(user.getId()).ifPresent(userEntity -> {
                userEntity.setUsername(user.getUsername());
                userEntity.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
                userEntity.setEmail(user.getEmail());
                userRepository.save(userEntity);
        });
        return true;
    }

    @Override
    public boolean deleteUser(String id) {
        userRepository.findById(id).ifPresent(userRepository::delete);
        return true;
    }

    @Override
    public User getUser(String id) {
        return new User(userRepository.findById(id).orElseThrow());
    }
}

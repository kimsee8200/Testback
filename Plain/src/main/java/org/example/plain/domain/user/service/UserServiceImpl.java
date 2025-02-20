package org.example.plain.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.example.plain.common.enums.Role;
import org.example.plain.domain.user.dto.UserRequestResponse;
import org.example.plain.domain.user.entity.User;
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
    public boolean createUser(UserRequestResponse userRequestResponse) {
        User user = new User(userRequestResponse);
        user.setPassword(bCryptPasswordEncoder.encode(userRequestResponse.getPassword()));
        user.setRole(Role.NORMAL);
        Objects.requireNonNull(user);
        userRepository.save(user);
        return true;
    }

    @Override
    public boolean updateUser(UserRequestResponse userRequestResponse) {
        userRepository.findById(userRequestResponse.getId()).ifPresent(userEntity -> {
                userEntity.setUsername(userRequestResponse.getUsername());
                if (userRequestResponse.getPassword() != null) {
                    userEntity.setPassword(bCryptPasswordEncoder.encode(userRequestResponse.getPassword()));
                }
                userEntity.setEmail(userRequestResponse.getEmail());
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
    public UserRequestResponse getUser(String id) {
        return new UserRequestResponse(userRepository.findById(id).orElseThrow());
    }

    @Override
    public UserRequestResponse getUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            return null;
        }
        return new UserRequestResponse(user);
    }


}

package org.example.plain.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.example.plain.domain.user.dto.CustomUserDetails;
import org.example.plain.domain.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new CustomUserDetails(userRepository.findById(username).orElseThrow(() -> new UsernameNotFoundException(username)));
    }
}

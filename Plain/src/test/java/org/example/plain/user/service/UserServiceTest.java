package org.example.plain.user.service;

import org.example.plain.domain.user.dto.UserRequest;
import org.example.plain.domain.user.dto.UserResponse;
import org.example.plain.domain.user.entity.User;
import org.example.plain.domain.user.interfaces.UserService;
import org.example.plain.domain.user.repository.UserRepository;
import org.example.plain.domain.user.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig
public class UserServiceTest {

    @MockBean
    private UserRepository userRepository;
    @MockBean
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserService userService;
    private List<UserRequest> userRequestRespons;

    @BeforeEach
    public void setUp() {
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(null);


        userRequestRespons = new ArrayList<>();

        UserRequest userRequest = new UserRequest("kimsee","박근택","go@email.com","1234");
        UserRequest userRequest2 = new UserRequest("김주호","ju@email");
        UserRequest userRequest3 = new UserRequest("rkedx","김갑든","kimgap@gmail.com","1234");
        UserRequest userRequest4 = new UserRequest("김주호","opt@email.com");

        userRequestRespons.add(userRequest);
        userRequestRespons.add(userRequest2);
        userRequestRespons.add(userRequest3);
        userRequestRespons.add(userRequest4);
    }

    @Test
    public void testSave() {
        userService = new UserServiceImpl(userRepository,bCryptPasswordEncoder);
        boolean result = userService.createUser(userRequestRespons.get(0));
        assertThat(result).isTrue();
    }

    @Test
    public void testGet(){
        Mockito.when(userRepository.findById(Mockito.eq("kimsee"))).thenReturn(Optional.of(new User(userRequestRespons.get(0))));
        Mockito.when(userRepository.findByEmail(Mockito.eq("go@email.com"))).thenReturn(Optional.of(new User(userRequestRespons.get(0))));

        userService = new UserServiceImpl(userRepository,bCryptPasswordEncoder);
        UserResponse userRequest = userService.getUserByEmail("go@email.com");
        UserResponse userRequest2 = userService.getUser("kimsee");

        assertThat(userRequest).isNotNull();
        assertThat(userRequest2).isNotNull();
        assertThat(userRequest).isEqualTo(userRequest2);
        assertThat(userRequest.email()).isEqualTo("go@email.com");
        assertThat(userRequest.username()).isEqualTo("박근택");
    }

    @Test
    public void testRemove(){
        Mockito.when(userRepository.findById(Mockito.eq("kimsee"))).thenReturn(Optional.of(new User(userRequestRespons.get(0))));
        Mockito.when(userRepository.findByEmail(Mockito.eq("go@email.com"))).thenReturn(Optional.of(new User(userRequestRespons.get(0))));

        userService = new UserServiceImpl(userRepository,bCryptPasswordEncoder);
        boolean result = userService.deleteUser("kimsee");

        assertThat(result).isTrue();
    }

    @Test
    public void testUpdate(){
        Mockito.when(userRepository.findById(Mockito.eq("kimsee"))).thenReturn(Optional.of(new User(userRequestRespons.get(0))));
        Mockito.when(userRepository.findByEmail(Mockito.eq("go@email.com"))).thenReturn(Optional.of(new User(userRequestRespons.get(0))));

        userService = new UserServiceImpl(userRepository,bCryptPasswordEncoder);
        boolean result = userService.updateUser(new UserRequest("kimsee","kim@email.com",null,null));

        assertThat(result).isTrue();
    }

}

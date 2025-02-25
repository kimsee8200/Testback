package org.example.plain.user.service;

import org.example.plain.domain.user.dto.UserRequestResponse;
import org.example.plain.domain.user.entity.User;
import org.example.plain.domain.user.interfaces.UserService;
import org.example.plain.domain.user.repository.UserRepository;
import org.example.plain.domain.user.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig
public class UserServiceTest {

    @MockitoBean
    private UserRepository userRepository;
    @MockitoBean
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserService userService;
    private List<UserRequestResponse> userRequestResponses;

    @BeforeEach
    public void setUp() {
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(null);


        userRequestResponses = new ArrayList<>();

        UserRequestResponse userRequestResponse = new UserRequestResponse("kimsee","박근택","go@email.com","1234");
        UserRequestResponse userRequestResponse2 = new UserRequestResponse("김주호","ju@email");
        UserRequestResponse userRequestResponse3 = new UserRequestResponse("rkedx","김갑든","kimgap@gmail.com","1234");
        UserRequestResponse userRequestResponse4 = new UserRequestResponse("김주호","opt@email.com");

        userRequestResponses.add(userRequestResponse);
        userRequestResponses.add(userRequestResponse2);
        userRequestResponses.add(userRequestResponse3);
        userRequestResponses.add(userRequestResponse4);
    }

    @Test
    public void testSave() {
        userService = new UserServiceImpl(userRepository,bCryptPasswordEncoder);
        boolean result = userService.createUser(userRequestResponses.get(0));
        assertThat(result).isTrue();
    }

    @Test
    public void testGet(){
        Mockito.when(userRepository.findById(Mockito.eq("kimsee"))).thenReturn(Optional.of(new User(userRequestResponses.get(0))));
        Mockito.when(userRepository.findByEmail(Mockito.eq("go@email.com"))).thenReturn(Optional.of(new User(userRequestResponses.get(0))));

        userService = new UserServiceImpl(userRepository,bCryptPasswordEncoder);
        UserRequestResponse userRequestResponse = userService.getUserByEmail("go@email.com");
        UserRequestResponse userRequestResponse2 = userService.getUser("kimsee");

        assertThat(userRequestResponse).isNotNull();
        assertThat(userRequestResponse2).isNotNull();
        assertThat(userRequestResponse).isEqualTo(userRequestResponse2);
        assertThat(userRequestResponse.getEmail()).isEqualTo("go@email.com");
        assertThat(userRequestResponse.getUsername()).isEqualTo("박근택");
        assertThat(userRequestResponse.getPassword()).isEqualTo("1234");
    }

    @Test
    public void testRemove(){
        Mockito.when(userRepository.findById(Mockito.eq("kimsee"))).thenReturn(Optional.of(new User(userRequestResponses.get(0))));
        Mockito.when(userRepository.findByEmail(Mockito.eq("go@email.com"))).thenReturn(Optional.of(new User(userRequestResponses.get(0))));

        userService = new UserServiceImpl(userRepository,bCryptPasswordEncoder);
        boolean result = userService.deleteUser("kimsee");

        assertThat(result).isTrue();
    }

    @Test
    public void testUpdate(){
        Mockito.when(userRepository.findById(Mockito.eq("kimsee"))).thenReturn(Optional.of(new User(userRequestResponses.get(0))));
        Mockito.when(userRepository.findByEmail(Mockito.eq("go@email.com"))).thenReturn(Optional.of(new User(userRequestResponses.get(0))));

        userService = new UserServiceImpl(userRepository,bCryptPasswordEncoder);
        boolean result = userService.updateUser(new UserRequestResponse("kimsee","kim@email.com",null,null));

        assertThat(result).isTrue();
    }

}

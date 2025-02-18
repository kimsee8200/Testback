package org.example.plain.user.service;

import org.example.plain.domain.user.dto.CustomOAuth2User;
import org.example.plain.domain.user.dto.User;
import org.example.plain.domain.user.entity.UserEntity;
import org.example.plain.domain.user.interfaces.UserService;
import org.example.plain.domain.user.repository.UserRepository;
import org.example.plain.domain.user.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
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
    private List<User> users;

    @BeforeEach
    public void setUp() {
        Mockito.when(userRepository.save(Mockito.any(UserEntity.class))).thenReturn(null);


        users = new ArrayList<>();

        User user = new User("kimsee","박근택","go@email.com","1234");
        User user2 = new User("김주호","ju@email");
        User user3 = new User("rkedx","김갑든","kimgap@gmail.com","1234");
        User user4 = new User("김주호","opt@email.com");

        users.add(user);
        users.add(user2);
        users.add(user3);
        users.add(user4);
    }

    @Test
    public void testSave() {
        userService = new UserServiceImpl(userRepository,bCryptPasswordEncoder);
        boolean result = userService.createUser(users.getFirst());
        assertThat(result).isTrue();
    }

    @Test
    public void testGet(){
        Mockito.when(userRepository.findById(Mockito.eq("kimsee"))).thenReturn(Optional.of(new UserEntity(users.get(0))));
        Mockito.when(userRepository.findByEmail(Mockito.eq("go@email.com"))).thenReturn(Optional.of(new UserEntity(users.get(0))));

        userService = new UserServiceImpl(userRepository,bCryptPasswordEncoder);
        User user = userService.getUserByEmail("go@email.com");
        User user2 = userService.getUser("kimsee");

        assertThat(user).isNotNull();
        assertThat(user2).isNotNull();
        assertThat(user).isEqualTo(user2);
        assertThat(user.getEmail()).isEqualTo("go@email.com");
        assertThat(user.getUsername()).isEqualTo("박근택");
        assertThat(user.getPassword()).isEqualTo("1234");
    }

    @Test
    public void testRemove(){
        Mockito.when(userRepository.findById(Mockito.eq("kimsee"))).thenReturn(Optional.of(new UserEntity(users.get(0))));
        Mockito.when(userRepository.findByEmail(Mockito.eq("go@email.com"))).thenReturn(Optional.of(new UserEntity(users.get(0))));

        userService = new UserServiceImpl(userRepository,bCryptPasswordEncoder);
        boolean result = userService.deleteUser("kimsee");

        assertThat(result).isTrue();
    }

    @Test
    public void testUpdate(){
        Mockito.when(userRepository.findById(Mockito.eq("kimsee"))).thenReturn(Optional.of(new UserEntity(users.get(0))));
        Mockito.when(userRepository.findByEmail(Mockito.eq("go@email.com"))).thenReturn(Optional.of(new UserEntity(users.get(0))));

        userService = new UserServiceImpl(userRepository,bCryptPasswordEncoder);
        boolean result = userService.updateUser(new User("kimsee","kim@email.com",null,null));

        assertThat(result).isTrue();
    }

}

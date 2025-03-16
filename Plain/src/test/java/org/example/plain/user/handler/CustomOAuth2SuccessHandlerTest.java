package org.example.plain.user.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.plain.domain.user.dto.CustomOAuth2User;
import org.example.plain.domain.user.dto.UserRequest;
import org.example.plain.domain.user.handler.CustomOAuth2SuccessHandler;
import org.example.plain.domain.user.service.JWTUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@TestPropertySource(properties = {"spring.jwt.expires=1800000","spring.jwt.refresh.expires=864000000"})
@TestPropertySource(properties = {"spring.jwt.screat=rjwltakfzldltkdqndnqns93dcxptmdxa"})
@SpringJUnitConfig(classes = {JWTUtil.class, ObjectMapper.class})
public class CustomOAuth2SuccessHandlerTest {

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private ObjectMapper objectMapper;

    private HttpServletRequest request;
    private HttpServletResponse response;
    private Authentication authentication;
    private List<CustomOAuth2User> users;

    @BeforeEach
    public void setUp() {
        request = Mockito.mock(HttpServletRequest.class);
        response = Mockito.mock(HttpServletResponse.class);
        users = new ArrayList<>();

        UserRequest userRequest = new UserRequest("kimsee","박근택","go@email.com","1234");
        UserRequest userRequest2 = new UserRequest("김주호","ju@email");
        UserRequest userRequest3 = new UserRequest("rkedx","김갑든","kimgap@gmail.com","1234");
        UserRequest userRequest4 = new UserRequest("김주호","opt@email.com");

        users.add(new CustomOAuth2User(userRequest));
        users.add(new CustomOAuth2User(userRequest2));
        users.add(new CustomOAuth2User(userRequest3));
        users.add(new CustomOAuth2User(userRequest4));

        authentication = new UsernamePasswordAuthenticationToken(users.get(0), "password");
    }

    @Test
    public void isNotExistingUser() throws Exception {
        authentication = new UsernamePasswordAuthenticationToken(users.get(1), "password");

        CustomOAuth2SuccessHandler oAuth2SuccessHandler = new CustomOAuth2SuccessHandler(jwtUtil,objectMapper);
        Mockito.when(response.getWriter()).thenReturn(Mockito.mock(PrintWriter.class));

        oAuth2SuccessHandler.onAuthenticationSuccess(request, response, authentication);

        Mockito.verify(response, Mockito.atLeastOnce()).setStatus(HttpServletResponse.SC_FOUND);
        Mockito.verify(response, Mockito.atLeastOnce()).setContentType(Mockito.eq("application/json"));
        Mockito.verify(response.getWriter(), Mockito.atLeastOnce()).write(Mockito.anyString());
        Mockito.verify(response, Mockito.times(1)).sendRedirect(Mockito.matches("/sign_in"));
    }

    @Test
    public void isExistingUser() throws Exception {
        CustomOAuth2SuccessHandler oAuth2SuccessHandler = new CustomOAuth2SuccessHandler(jwtUtil,objectMapper);
        Mockito.when(response.getWriter()).thenReturn(Mockito.mock(PrintWriter.class));

        oAuth2SuccessHandler.onAuthenticationSuccess(request, response, authentication);

        Mockito.verify(response,Mockito.atLeastOnce()).setStatus(HttpServletResponse.SC_OK);
        Mockito.verify(response,Mockito.atLeastOnce()).addCookie(Mockito.any());
        Mockito.verify(response,Mockito.atLeastOnce()).addHeader(eq("Authorization"),Mockito.anyString());
    }
}

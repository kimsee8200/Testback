package org.example.plain.user.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.plain.common.ResponseBody;
import org.example.plain.domain.user.dto.CustomUserDetails;
import org.example.plain.domain.user.dto.UserRequest;
import org.example.plain.domain.user.entity.User;
import org.example.plain.domain.user.filters.LoginFilter;
import org.example.plain.domain.user.service.JWTUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.InputStream;
import java.io.PrintWriter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;

@TestPropertySource(properties = {"spring.jwt.expires=1800000","spring.jwt.refresh.expires=864000000"})
@TestPropertySource(properties = {"spring.jwt.screat=rjwltakfzldltkdqndnqns93dcxptmdxa"})
@SpringJUnitConfig(classes = {JWTUtil.class})
public class LoginFilterTest {

    @Autowired
    private JWTUtil jwtUtil;
    private AuthenticationManager authenticationManager;
    private ObjectMapper objectMapper;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private UserRequest userRequest;

    @BeforeEach
    public void init(){
        authenticationManager = Mockito.mock(AuthenticationManager.class);
        objectMapper = Mockito.mock(ObjectMapper.class);
        request = Mockito.mock(HttpServletRequest.class);
        response = Mockito.mock(HttpServletResponse.class);
        userRequest = new UserRequest("test","park","park@gmail.com","test1234");
    }

    @Test
    @DisplayName("authentication manager까지의 과정 진행 검증.")
    public void passToAuthenticationManager() throws Exception{
        Mockito.doReturn(userRequest).when(objectMapper).readValue(Mockito.any(InputStream.class), Mockito.any(Class.class));
        Mockito.doReturn(Mockito.mock(ServletInputStream.class)).when(request).getInputStream();

        ArgumentCaptor<UsernamePasswordAuthenticationToken> chapter = ArgumentCaptor.forClass(UsernamePasswordAuthenticationToken.class);

        LoginFilter loginFilter = new LoginFilter(jwtUtil, authenticationManager, objectMapper);
        loginFilter.attemptAuthentication(request, response);

        Mockito.verify(authenticationManager).authenticate(Mockito.any(Authentication.class));
        Mockito.verify(authenticationManager).authenticate(chapter.capture());


        assertThat(chapter.getValue().getPrincipal()).isEqualTo(userRequest.getId());
    }

    @Test
    public void isAuthenticationSuccess() throws Exception{
        LoginFilter loginFilter = new LoginFilter(jwtUtil, authenticationManager, objectMapper);

        FilterChain chain = Mockito.mock(FilterChain.class);
        Authentication authentication = Mockito.mock(Authentication.class);

        Mockito.when(authentication.getPrincipal()).thenReturn(new CustomUserDetails(new User(userRequest)));

        ReflectionTestUtils.invokeMethod(loginFilter, "successfulAuthentication", request, response, chain, authentication);

        Mockito.verify(response,Mockito.atLeastOnce()).setStatus(HttpServletResponse.SC_OK);
        Mockito.verify(response,Mockito.atLeastOnce()).addCookie(Mockito.any());
        Mockito.verify(response,Mockito.atLeastOnce()).addHeader(eq("Authorization"),Mockito.anyString());
        Mockito.verify(response,Mockito.never()).sendRedirect(Mockito.anyString());
    }

    @Test
    public void isAuthenticationFailed() throws Exception{
        LoginFilter loginFilter = new LoginFilter(jwtUtil, authenticationManager, objectMapper);

        AuthenticationException failure = Mockito.mock(AuthenticationException.class);

        Mockito.when(failure.getMessage()).thenReturn("test 실패 당함");
        Mockito.when(response.getWriter()).thenReturn(Mockito.mock(PrintWriter.class));
        Mockito.when(objectMapper.writeValueAsString(any(ResponseBody.class))).thenReturn("ResponseBody");


        ReflectionTestUtils.invokeMethod(loginFilter, "unsuccessfulAuthentication", request, response, failure);

        Mockito.verify(response,Mockito.atLeastOnce()).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        Mockito.verify(response,Mockito.atLeastOnce()).setContentType(eq("application/json"));
        Mockito.verify(response.getWriter(),Mockito.atLeastOnce()).write(Mockito.anyString());
        Mockito.verify(response,Mockito.never()).sendRedirect(Mockito.anyString());
    }

    @Test
    public void makeCookie() throws Exception {
        LoginFilter loginFilter = new LoginFilter(jwtUtil, authenticationManager, objectMapper);

        Cookie cookie = ReflectionTestUtils.invokeMethod(loginFilter, "makeCookie", "tookenteeessst");

        assertThat(cookie.getValue()).isEqualTo("tookenteeessst");
        assertThat(cookie.getMaxAge()).isEqualTo(60*60*24);
        assertThat(cookie.getName()).isEqualTo("token");
    }
}

package org.example.plain.user.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.plain.common.ResponseBody;
import org.example.plain.domain.user.service.JWTUtil;
import org.example.plain.domain.user.service.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@TestPropertySource(properties = {"spring.jwt.expires=1800000","spring.jwt.refresh.expires=864000000"})
@TestPropertySource(properties = {"spring.jwt.screat=rjwltakfzldltkdqndnqns93dcxptmdxa"})
@SpringJUnitConfig(classes = JWTUtil.class)
public class TokenServiceTest {
    @Autowired
    private JWTUtil jwtUtil;
    private TokenService tokenService;
    private HttpServletRequest request;
    private HttpServletResponse response;

    @BeforeEach
    public void setUp() {
        tokenService = new TokenService(jwtUtil);
        request = Mockito.mock(HttpServletRequest.class);
        response = Mockito.mock(HttpServletResponse.class);
    }

    @Test
    public void isReissued() {
        Cookie cookie = new Cookie("token", jwtUtil.makeRefreshToken("test"));
        Mockito.when(request.getCookies()).thenReturn(new Cookie[]{cookie});

        ResponseBody result = tokenService.reissue(request, response);

        assertThat(result.getStatus()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isNotNull();
    }

    @Test
    public void isTokenNull(){
        Mockito.when(request.getCookies()).thenReturn(new Cookie[]{});

        ResponseBody result = tokenService.reissue(request, response);

        assertThat(result.getStatus()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(result.getBody()).isNull();
        assertThat(result.getMessage()).isEqualTo("토큰이 존재하지 않습니다..");
    }

    @Test
    public void isUnvalidToken(){
        Cookie cookie = new Cookie("token", jwtUtil.makeJwtToken("test"));
        Mockito.when(request.getCookies()).thenReturn(new Cookie[]{cookie});

        ResponseBody result = tokenService.reissue(request, response);

        assertThat(result.getStatus()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(result.getBody()).isNull();
        assertThat(result.getMessage()).isEqualTo("지원되지 않는 토큰 입니다.");
    }


}

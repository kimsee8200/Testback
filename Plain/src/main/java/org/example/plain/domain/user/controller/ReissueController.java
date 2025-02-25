package org.example.plain.domain.user.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.plain.common.ResponseBody;
import org.example.plain.common.enums.Message;
import org.example.plain.domain.user.service.JWTUtil;
import org.example.plain.domain.user.service.TokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequiredArgsConstructor
public class ReissueController {

    private final JWTUtil jwtUtil;
    private final TokenService tokenService;

    @PostMapping("/reissue")
    public ResponseEntity<ResponseBody> reissue(HttpServletRequest request, HttpServletResponse response) {
        ResponseBody responseBody = tokenService.reissue(request, response);
        if (responseBody.getStatus().is4xxClientError()) {
            return new ResponseEntity<>(responseBody,HttpStatus.UNAUTHORIZED);
        }else {
            response = (HttpServletResponse) responseBody.getBody();
            responseBody.setBody(null);
            return new ResponseEntity<>(responseBody, HttpStatus.OK);
        }
    }

    private Cookie makeCookie(String key){
        Cookie cookie = new Cookie("token",key);
        cookie.setMaxAge(60*60*24);
        cookie.setSecure(true);
        return cookie;
    }
}

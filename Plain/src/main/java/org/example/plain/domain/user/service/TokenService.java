package org.example.plain.domain.user.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.plain.common.ResponseBody;
import org.example.plain.common.enums.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final JWTUtil jwtUtil;

    public ResponseBody reissue(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookie = request.getCookies();
        String token = null;
        for(Cookie c : cookie) {
            if(c.getName().equals("token")) {
                token = c.getValue();
                break;
            }
        }

        if (token == null) {
            return new ResponseBody<>("토큰이 존재하지 않습니다..", HttpStatus.UNAUTHORIZED,null);
        }

        String id = jwtUtil.getId(token);

        if (jwtUtil.isExpired(token)) {
            return new ResponseBody<>("토큰이 만료되었습니다.", HttpStatus.UNAUTHORIZED,null);
        }

        if (jwtUtil.getType(token).equals("refresh")) {
            response.addHeader("Authorization", jwtUtil.makeJwtToken(id));
            response.addCookie(makeCookie(jwtUtil.makeRefreshToken(id)));
            return new ResponseBody<>(Message.OK.name(), HttpStatus.OK, response);
        }else
            return new ResponseBody<>("지원되지 않는 토큰 입니다.", HttpStatus.UNAUTHORIZED,null);

    }

    private Cookie makeCookie(String key){
        Cookie cookie = new Cookie("token",key);
        cookie.setMaxAge(60*60*24);
        cookie.setSecure(true);
        return cookie;
    }
}

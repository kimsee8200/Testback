package org.example.plain.domain.user.filters;


import com.sun.net.httpserver.HttpExchange;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.plain.common.enums.Role;
import org.example.plain.domain.user.dto.CustomUserDetails;
import org.example.plain.domain.user.entity.UserEntity;
import org.example.plain.domain.user.service.JWTUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import javax.xml.crypto.dsig.spec.XPathType;
import java.io.IOException;
import java.io.InvalidObjectException;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");

        if (token == null){
            filterChain.doFilter(request, response);
            return;
        }
        if(!jwtUtil.getType(token).equals("access")){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().println("invalid token");
        }
        if(jwtUtil.isExpired(token)){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().println("token is expired");
            return;
        }

        CustomUserDetails customUserDetails = new CustomUserDetails(
                new UserEntity().builder()
                        .id(jwtUtil.getId(token))
                        .role(Role.NORMAL)
                        .build()
        );

        Authentication authentication = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
        SecurityContextHolder.getContextHolderStrategy().getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }
}

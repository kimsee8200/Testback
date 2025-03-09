package org.example.plain.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import org.example.plain.domain.user.dto.OAuth2Response;
import org.example.plain.domain.user.filters.JwtFilter;
import org.example.plain.domain.user.filters.LoginFilter;
import org.example.plain.domain.user.handler.CustomOAuth2SuccessHandler;
import org.example.plain.domain.user.interfaces.UserService;
import org.example.plain.domain.user.repository.UserRepository;
import org.example.plain.domain.user.service.CustomOauth2UserService;
import org.example.plain.domain.user.service.CustomUserDetailsService;
import org.example.plain.domain.user.service.JWTUtil;
import org.example.plain.domain.user.service.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.NullRememberMeServices;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity(debug = true)
@RequiredArgsConstructor
public class SecurityConfig {
    private final UserRepository userRepository;
    private final CustomUserDetailsService userDetailsService;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final JWTUtil jwtUtil;
    private final CustomOauth2UserService oauth2UserService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserService userService() {
        return new UserServiceImpl(userRepository, passwordEncoder());
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(form -> form.disable())
                .userDetailsService(userDetailsService)
                .addFilterAt(new LoginFilter(jwtUtil, authenticationConfiguration.getAuthenticationManager(),objectMapper), UsernamePasswordAuthenticationFilter.class)
                .addFilterAt(new JwtFilter(jwtUtil), LoginFilter.class)
                .authorizeHttpRequests(authorizeRequests ->
                        {
                            // sing_in은 회원가입 페이지로 이동.
                            authorizeRequests.requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**",
                                    "/swagger-resources/**", "/webjars/**", "/api-docs/**").permitAll();
                            authorizeRequests.requestMatchers("/account/create","/login","/","/sign_up").permitAll();
                            authorizeRequests.anyRequest().authenticated();
                        }
                )
                .logout(logout -> {
                    logout.logoutUrl("/account/logout");
                    logout.logoutSuccessUrl("/");
                })
                .oauth2Login(oauth2 ->
                        oauth2.authorizationEndpoint(authorizationEndpoint ->
                                        authorizationEndpoint.baseUri("/user/login")
                                ).userInfoEndpoint(userInfoEndpoint ->
                                        userInfoEndpoint.userService(oauth2UserService)
                                ).successHandler(new CustomOAuth2SuccessHandler(jwtUtil,objectMapper)
                        )

                )
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}

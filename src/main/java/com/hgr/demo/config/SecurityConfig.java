package com.hgr.demo.config;

import com.hgr.demo.jwt.JwtAuthenticationFilter;
import com.hgr.demo.jwt.JwtTokenProvider;
import com.hgr.demo.service.MemberService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomAccessDeniedHandler accessDeniedHandler;

    public SecurityConfig(MemberService memberService, JwtTokenProvider jwtTokenProvider, CustomAccessDeniedHandler accessDeniedHandler) {
        this.memberService = memberService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.accessDeniedHandler = accessDeniedHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/").permitAll()
                        .requestMatchers(PathRequest.toH2Console()).permitAll()
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .requestMatchers("/api/auth").permitAll()
                        .anyRequest().authenticated()
                )

                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)

//                .userDetailsService(memberService)
                .exceptionHandling(handling -> handling
                                .accessDeniedHandler(accessDeniedHandler)   //403
                        //.authenticationEntryPoint()   //401
                        //.authenticationFailureHandler()
                        //.authenticationSuccessHandler()
                        //.logoutSuccessHandler()
                        //.sessionInformationExpiredStrategy()
                )

                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
                .build();

    }
}

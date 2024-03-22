package com.hgr.demo.config;

import com.hgr.demo.service.CustomUserDetailsService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity hs) throws Exception {

        return hs.csrf(csrfConfig -> csrfConfig.disable())

                .headers(headerConfig -> headerConfig
                        .frameOptions(frameOptionsConfig -> frameOptionsConfig.disable())
                )

                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        //.requestMatchers(PathRequest.toH2Console()).permitAll()
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/home").permitAll()
                        .requestMatchers(request -> request.getServletPath().endsWith(".html")).permitAll()
                        //.requestMatchers("/posts/**", "/login/**").hasRole("USER")
                        .anyRequest().authenticated()
                )

                .formLogin(formLogin -> formLogin
                        .loginPage("/login")    //커스텀 로그인 페이지 주소
                        .loginProcessingUrl("/login-process")    //로그인 페이지를 login으로 하면 이 항목을 별도 지정해야 무한루프가 없음
                        .usernameParameter("mem_lgn_id")    //아이디 값
                        .passwordParameter("mem_lgn_pw")    //비밀번호 값
                        .defaultSuccessUrl("/")
                        .permitAll()
                )

                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .permitAll()
                )

//                .httpBasic(Customizer.withDefaults())
                .userDetailsService(customUserDetailsService)
//                .passwordEncoder(passwordEncoder())
                .build();

    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }
}

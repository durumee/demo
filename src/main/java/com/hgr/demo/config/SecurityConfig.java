package com.hgr.demo.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
    @Bean 
    public BCryptPasswordEncoder encodePwd() {
        return new BCryptPasswordEncoder();
    }

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
			.requestMatchers("/main").permitAll()
			.requestMatchers(request -> request.getServletPath().endsWith(".html")).permitAll()
			//.requestMatchers("/posts/**", "/login/**").hasRole("USER")
			.anyRequest().authenticated()
		)
		
//		.formLogin(login -> login
//                .loginPage("/view/login")	// [A] 커스텀 로그인 페이지 지정
//                .loginProcessingUrl("/login-process")	// [B] submit 받을 url
//                .usernameParameter("userid")	// [C] submit할 아이디
//                .passwordParameter("userpw")	// [D] submit할 비밀번호
//                .defaultSuccessUrl("/view/dashboard", true)
//                .permitAll()
//        )
		
		.httpBasic(Customizer.withDefaults())
		.build();
		
	}

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
        		.requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }
}


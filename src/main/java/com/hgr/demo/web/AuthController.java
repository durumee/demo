package com.hgr.demo.web;

import com.hgr.demo.dto.MemberDTO;
import com.hgr.demo.jwt.JwtTokenProvider;
import com.hgr.demo.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthController {
    private final JwtTokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final MemberService memberService;

    public AuthController(JwtTokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder, MemberService memberService) {
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.memberService = memberService;
    }

    @PostMapping("/auth")
    public ResponseEntity<String> authorize(@RequestBody MemberDTO loginDto, HttpServletResponse response) {
//        memberService.loadUserByUsername();
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        //액세스 토큰 생성
        String accessToken = tokenProvider.generateToken(authentication);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + accessToken);

        //리프레시 토큰 생성
        String refreshToken = tokenProvider.generateRefreshToken(authentication);
        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setPath("/refresh-token");
        response.addCookie(refreshTokenCookie);

        return ResponseEntity.ok().headers(httpHeaders).body("Login successful");
    }

    @GetMapping("/validate-token")
    public ResponseEntity<String> validateToken(@RequestHeader("Authorization") String token) {
        try {
            // 토큰에서 "Bearer " 접두사 제거
            token = token.replace("Bearer ", "");

            // 토큰 검증
            boolean isValid = tokenProvider.validateToken(token);

            if (isValid) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }
    }

    //FIXME: 전체 테스트 되지 않음, 프론트엔드 포함
    @PostMapping("/refresh-token")
    public ResponseEntity<String> refresh(@CookieValue("refreshToken") String refreshToken, HttpServletResponse response) {
        if (tokenProvider.validateToken(refreshToken)) {
            String username = tokenProvider.getUsername(refreshToken);
            UserDetails loginDto = memberService.loadUserByUsername(username);

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(username, loginDto.getPassword());

            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String newAccessToken = tokenProvider.generateToken(authentication);
            String newRefreshToken = tokenProvider.generateRefreshToken(authentication);

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Authorization", "Bearer " + newAccessToken);

            Cookie newRefreshTokenCookie = new Cookie("refreshToken", newRefreshToken);
            newRefreshTokenCookie.setHttpOnly(true);
            newRefreshTokenCookie.setPath("/refresh-token");
            response.addCookie(newRefreshTokenCookie);

            return ResponseEntity.ok().headers(httpHeaders).body("Login successful");
        } else {
            return ResponseEntity.status(401).body("Invalid refresh token");
        }
    }
}
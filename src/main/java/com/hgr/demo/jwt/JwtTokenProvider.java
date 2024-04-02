package com.hgr.demo.jwt;

import com.hgr.demo.service.MemberService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JwtTokenProvider {
    private final MemberService userDetailsService;
    private SecretKey secretKey;

    @Value("${jwt.secretKey}")
    private String secretKeyPlain;
    @Value("${jwt.expirationMs}")
    private long expirationMs;

    public JwtTokenProvider(MemberService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @PostConstruct
    protected void init() {
        log.info(":: JWT Init ::");
        String secretKeyEncoded = Base64.getEncoder().encodeToString(secretKeyPlain.getBytes());
        this.secretKey = new SecretKeySpec(
                secretKeyEncoded.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm()
        );
    }

    public String createToken(Authentication authentication) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + expirationMs);

        return Jwts.builder()
                .subject(authentication.getName())
                .claim("username", authentication.getName())
                .issuedAt(now)
                .expiration(expiration)
                .signWith(secretKey)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

//    public Authentication getAuthentication(String token) {
//        Claims claims = Jwts.parser()
//                .verifyWith(secretKey)
//                .build()
//                .parseSignedClaims(token)
//                .getPayload();
//
//        String username = claims.getSubject();
//        List<String> roles = claims.get("roles", List.class);
//
//        List<SimpleGrantedAuthority> authorities = roles.stream()
//                .map(SimpleGrantedAuthority::new)
//                .collect(Collectors.toList());
//
//        UserDetails userDetails = User.builder()
//                .username(username)
//                .password("")
//                .authorities(authorities)
//                .build();
//
//        return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
//    }

    public String getUsername(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration()
                .after(new Date());
    }
}

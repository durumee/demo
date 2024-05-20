package com.hgr.demo.jwt;

import com.hgr.demo.exceptions.InvalidTokenException;
import com.hgr.demo.exceptions.TokenExpiredException;
import com.hgr.demo.service.MemberService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

@Component
@Slf4j
public class JwtTokenProvider {
    private final MemberService userDetailsService;
    private SecretKey secretKey;

    @Value("${jwt.secretKeyPlain}")
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
        log.info(":: JWT Init 2 secretKeyPlain :: {}", secretKeyPlain);
        log.info(":: JWT Init 2 expirationMs :: {}", expirationMs);
        this.secretKey = new SecretKeySpec(
                secretKeyEncoded.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm()
        );
    }

    public String createToken(Authentication authentication) {
        log.info("cr token :: {}", expirationMs);
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
        String usernameFromToken = getUsername(token);
        log.info("usernameFromToken :: {}", usernameFromToken);
        UserDetails userDetails = userDetailsService.loadUserByUsername(usernameFromToken);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    //http 헤더에서 토큰 가져오기
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    //만료여부 검증
    public boolean validateToken(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration()
                .after(new Date());
    }

    //토큰 파싱
    public Claims parseToken(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (ExpiredJwtException e) {
            throw new TokenExpiredException("Token has expired");
        } catch (JwtException e) {
            throw new InvalidTokenException("Invalid token");
        }
    }
}

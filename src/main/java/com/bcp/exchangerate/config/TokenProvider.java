package com.bcp.exchangerate.config;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenProvider implements InitializingBean {
    private static final String AUTHORITIES_KEY = "rol";
    private static final String ROLE_USER = "USER";
    private static final String ENCODED_KEY_ALGORITHM = "HmacSHA512";
    private Key accessKey;
    @Value("${security.jwt.token-seconds}")
    private Long accessTokenInSeconds;
    @Value("${security.jwt.base64-key}")
    private String base64Secret;

    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes;
        log.debug("Using a Base64-encoded JWT secret key");
        keyBytes = Base64.getDecoder().decode(this.base64Secret);
        this.accessKey = new SecretKeySpec(keyBytes, ENCODED_KEY_ALGORITHM);
    }

    public Authentication getAuthentication(String accessToken) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(accessKey)
                    .parseClaimsJws(accessToken)
                    .getBody();
            return new UsernamePasswordAuthenticationToken(claims.getSubject(), accessToken, AuthorityUtils.createAuthorityList("ROL_USER"));
        } catch (Exception ex) {
            log.error("Error getting Token",ex);
            return null;
        }


    }

    public String createToken(String userName, LocalDateTime startTimeToken) {
        Key key;
        LocalDateTime validity;
        try {
            validity = startTimeToken.plusSeconds(this.accessTokenInSeconds);
            key = accessKey;
            return Jwts.builder()
                    .setId(UUID.randomUUID().toString())
                    .setSubject(userName)
                    .claim(AUTHORITIES_KEY, ROLE_USER)
                    .signWith(SignatureAlgorithm.HS512, key)
                    .setIssuedAt(Timestamp.valueOf(startTimeToken))
                    .setExpiration(Timestamp.valueOf(validity))
                    .compact();
        } catch (Exception e) {
            log.error("Error creating el JWT: {}", e.getMessage());
        }
        return null;
    }

}
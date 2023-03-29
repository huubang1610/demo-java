package com.config.swagger.config.security;

import com.config.swagger.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Component
@AllArgsConstructor
public class JwtTokenUtil implements Serializable {

    private static final String SIGNING_KEY = "bangph123";
    private static final long TOKEN_DURATION_IN_SECOND = Duration.ofDays(1).getSeconds();
    private final RedisTemplate<String, User> redisTemplate;

    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", user.getEmail());
        claims.put("username", user.getUserName());
        claims.put("id", user.getId());
        claims.put("gender", user.getGender());
        String token = doGenerateToken(user.getId().toString(), claims);
        // save token to redis with defaultDuration
        redisTemplate.opsForValue().set(token, user, TOKEN_DURATION_IN_SECOND);
        return token;
    }

    private String doGenerateToken(String jti, Map<String, Object> mapClm) {
        Claims claims = Jwts.claims().setId(jti);
        claims.putAll(mapClm);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_DURATION_IN_SECOND * 1000))
                .signWith(SignatureAlgorithm.HS256, SIGNING_KEY)
                .compact();

    }
    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(SIGNING_KEY).parseClaimsJws(authToken);
            return redisTemplate.hasKey(authToken) && !isTokenExpired(authToken);
        } catch (Exception e) {
            log.error("Fail to validate token {}", e.getMessage());
        }
        return false;
    }
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(SIGNING_KEY).parseClaimsJws(token).getBody();
    }

    public Long getUserIdFromJWT(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SIGNING_KEY)
                    .parseClaimsJws(token)
                    .getBody();

            return Long.parseLong(claims.getId());
        } catch (Exception e) {
            return null;
        }
    }
}
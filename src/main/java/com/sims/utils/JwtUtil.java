package com.sims.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT工具类
 * @author Diamond
 * @create 2025-11-19
 */
@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    /**
     * 获取签名密钥
     */
    private SecretKey getSignKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 生成JWT Token
     * @param userId 用户ID
     * @param username 用户名
     * @return token
     */
    public String generateToken(Long userId, String username) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("username", username);
        return createToken(claims, username);
    }

    /**
     * 创建Token
     * @param claims 载荷信息
     * @param subject 主题
     * @return token
     */
    private String createToken(Map<String, Object> claims, String subject) {
        // 获取当前时间作为token签发时间
        Date now = new Date();
        // 计算token过期时间
        Date expirationDate = new Date(now.getTime() + expiration);

        // 使用JJWT库构建JWT token
        // 使用JJWT库的Builder模式构建JWT token
        return Jwts.builder()
                // 设置载荷信息，包含自定义的用户信息
                .claims(claims)
                // 设置主题，通常为用户名
                .subject(subject)
                // 设置token签发时间
                .issuedAt(now)
                // 设置token过期时间
                .expiration(expirationDate)
                // 使用指定的签名密钥进行签名
                .signWith(getSignKey())
                // 生成压缩后的JWT字符串
                .compact();
    }

    /**
     * 从Token中获取Claims
     * @param token JWT Token
     * @return Claims
     */
    public Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * 从Token中获取用户ID
     * @param token JWT Token
     * @return 用户ID
     */
    public Long getUserIdFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.get("userId", Long.class);
    }

    /**
     * 从Token中获取用户名
     * @param token JWT Token
     * @return 用户名
     */
    public String getUsernameFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.get("username", String.class);
    }

    /**
     * 验证Token是否过期
     * @param token JWT Token
     * @return true=已过期 false=未过期
     */
    public boolean isTokenExpired(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            Date expiration = claims.getExpiration();
            return expiration.before(new Date());
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * 验证Token是否有效
     * @param token JWT Token
     * @return true=有效 false=无效
     */
    public boolean validateToken(String token) {
        try {
            getClaimsFromToken(token);
            return !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获取Token的剩余有效期（秒）
     * @param token JWT Token
     * @return 剩余有效期（秒），如果已过期返回0
     */
    public Long getTokenRemainingTime(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            Date expiration = claims.getExpiration();
            long remainingTime = expiration.getTime() - System.currentTimeMillis();
            // 如果已过期，返回0
            return remainingTime > 0 ? remainingTime / 1000 : 0L;
        } catch (Exception e) {
            return 0L;
        }
    }
}

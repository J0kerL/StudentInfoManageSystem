package com.sims.service.impl;

import com.sims.service.TokenBlacklistService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * Token黑名单服务实现类
 * @author Diamond
 * @create 2025-11-21
 */
@Slf4j
@Service
public class TokenBlacklistServiceImpl implements TokenBlacklistService {

    /**
     * Redis键前缀
     */
    private static final String BLACKLIST_PREFIX = "token:blacklist:";

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 将token加入黑名单
     * @param token JWT token
     * @param expireSeconds token剩余过期时间（秒）
     */
    @Override
    public void addToBlacklist(String token, Long expireSeconds) {
        String key = BLACKLIST_PREFIX + token;
        // 将token存入Redis，值设为当前时间戳，过期时间设置为token的剩余有效期
        redisTemplate.opsForValue().set(key, System.currentTimeMillis(), expireSeconds, TimeUnit.SECONDS);
        log.info("Token已加入黑名单，剩余有效期: {} 秒", expireSeconds);
    }

    /**
     * 检查token是否在黑名单中
     * @param token JWT token
     * @return true=在黑名单中 false=不在黑名单中
     */
    @Override
    public boolean isBlacklisted(String token) {
        String key = BLACKLIST_PREFIX + token;
        return redisTemplate.hasKey(key);
    }

    /**
     * 从黑名单中移除token
     * @param token JWT token
     */
    @Override
    public void removeFromBlacklist(String token) {
        String key = BLACKLIST_PREFIX + token;
        redisTemplate.delete(key);
        log.info("Token已从黑名单中移除");
    }
}

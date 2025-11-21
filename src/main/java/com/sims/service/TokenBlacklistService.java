package com.sims.service;

/**
 * Token黑名单服务接口
 * @author Diamond
 * @create 2025-11-21
 */
public interface TokenBlacklistService {

    /**
     * 将token加入黑名单
     * @param token JWT token
     * @param expireSeconds token剩余过期时间（秒）
     */
    void addToBlacklist(String token, Long expireSeconds);

    /**
     * 检查token是否在黑名单中
     * @param token JWT token
     * @return true=在黑名单中 false=不在黑名单中
     */
    boolean isBlacklisted(String token);

    /**
     * 从黑名单中移除token（一般由Redis自动过期，此方法用于特殊情况）
     * @param token JWT token
     */
    void removeFromBlacklist(String token);
}

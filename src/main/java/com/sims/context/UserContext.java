package com.sims.context;

/**
 * ThreadLocal工具类，用于存储当前登录用户信息
 * @author Diamond
 * @create 2025-11-19
 */
public class UserContext {

    private static final ThreadLocal<Long> userIdThreadLocal = new ThreadLocal<>();
    private static final ThreadLocal<String> usernameThreadLocal = new ThreadLocal<>();

    /**
     * 设置当前用户ID
     * @param userId 用户ID
     */
    public static void setUserId(Long userId) {
        userIdThreadLocal.set(userId);
    }

    /**
     * 获取当前用户ID
     * @return 用户ID
     */
    public static Long getUserId() {
        return userIdThreadLocal.get();
    }

    /**
     * 设置当前用户名
     * @param username 用户名
     */
    public static void setUsername(String username) {
        usernameThreadLocal.set(username);
    }

    /**
     * 获取当前用户名
     * @return 用户名
     */
    public static String getUsername() {
        return usernameThreadLocal.get();
    }

    /**
     * 设置当前用户信息
     * @param userId 用户ID
     * @param username 用户名
     */
    public static void setUser(Long userId, String username) {
        userIdThreadLocal.set(userId);
        usernameThreadLocal.set(username);
    }

    /**
     * 清除当前用户信息
     */
    public static void clear() {
        userIdThreadLocal.remove();
        usernameThreadLocal.remove();
    }
}

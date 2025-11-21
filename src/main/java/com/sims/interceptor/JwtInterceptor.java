package com.sims.interceptor;

import com.alibaba.fastjson.JSON;
import com.sims.context.UserContext;
import com.sims.result.Result;
import com.sims.service.TokenBlacklistService;
import com.sims.utils.JwtUtil;
import io.micrometer.common.util.StringUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * JWT拦截器，用于验证Token并设置用户上下文
 * @author Diamond
 * @create 2025-11-19
 */
@Slf4j
@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Resource
    private JwtUtil jwtUtil;

    @Resource
    private TokenBlacklistService tokenBlacklistService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        log.debug("进入JWT拦截器，请求路径: {}", request.getRequestURI());

        // 从请求头中获取Token
        String token = request.getHeader("Authorization");

        // 如果token为空或不是以Bearer开头
        if (StringUtils.isBlank(token)) {
            log.warn("请求未携带Token，访问路径: {}", request.getRequestURI());
            responseUnauthorized(response, "未登录，请先登录");
            return false;
        }

        // 去掉Bearer前缀（如果有）
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        // 检查token是否在黑名单中
        if (tokenBlacklistService.isBlacklisted(token)) {
            log.warn("Token已失效（在黑名单中），访问路径: {}", request.getRequestURI());
            responseUnauthorized(response, "token已失效，请重新登录");
            return false;
        }

        // 验证Token
        try {
            if (!jwtUtil.validateToken(token)) {
                log.warn("Token验证失败或已过期，访问路径: {}", request.getRequestURI());
                responseUnauthorized(response, "token已过期，请重新登录");
                return false;
            }

            // 从Token中获取用户信息并设置到ThreadLocal
            Long userId = jwtUtil.getUserIdFromToken(token);
            String username = jwtUtil.getUsernameFromToken(token);
            UserContext.setUser(userId, username);

            log.debug("Token验证成功，用户ID: {}, 用户名: {}", userId, username);
            return true;

        } catch (Exception e) {
            log.error("Token解析异常，访问路径: {}, 异常信息: {}", request.getRequestURI(), e.getMessage());
            responseUnauthorized(response, "Token无效");
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.debug("请求处理完成，请求路径: {}", request.getRequestURI());
        // 请求完成后清除ThreadLocal，避免内存泄漏
        UserContext.clear();
        log.debug("清除用户上下文，请求路径: {}", request.getRequestURI());
    }

    /**
     * 响应未授权信息
     */
    private void responseUnauthorized(HttpServletResponse response, String message) throws Exception {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");
        Result<String> result = Result.error(401, message);
        response.getWriter().write(JSON.toJSONString(result));
    }
}

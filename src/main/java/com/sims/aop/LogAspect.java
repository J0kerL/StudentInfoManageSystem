package com.sims.aop;

import com.alibaba.fastjson.JSON;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;

/**
 * @author Administrator
 */
@Slf4j
@Aspect
@Component
public class LogAspect {

    @Pointcut("execution(* com.sims.controller..*.*(..))")
    public void logPointcut() {
    }

    @Around("logPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {

        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        // 请求信息
        log.info("【请求】URL: {}, 方法: {}, 参数: {}",
                request.getRequestURI(),
                request.getMethod(),
                Arrays.toString(joinPoint.getArgs()));

        long start = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        log.info("【响应】耗时: {}ms, 返回: {}",
                System.currentTimeMillis() - start,
                JSON.toJSONString(result));

        return result;
    }
}

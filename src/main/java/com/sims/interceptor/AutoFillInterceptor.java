package com.sims.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.*;

/**
 * MyBatis拦截器，自动填充创建时间和更新时间
 *
 * @author Diamond
 * @create 2025-11-24
 */
@Slf4j
@Component
@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})
})
public class AutoFillInterceptor implements Interceptor {

    private static final String CREATE_TIME = "createTime";
    private static final String UPDATE_TIME = "updateTime";

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        Object parameter = invocation.getArgs()[1];
        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();

        // 只处理INSERT和UPDATE操作
        if (parameter != null && (sqlCommandType == SqlCommandType.INSERT || sqlCommandType == SqlCommandType.UPDATE)) {
            fillTimeFields(parameter, sqlCommandType);
        }

        return invocation.proceed();
    }

    /**
     * 填充时间字段
     */
    private void fillTimeFields(Object parameter, SqlCommandType sqlCommandType) {
        // 处理Map参数（使用@Param注解的情况）
        if (parameter instanceof Map) {
            Set<Object> processed = Collections.newSetFromMap(new IdentityHashMap<>());
            for (Object value : ((Map<?, ?>) parameter).values()) {
                if (isEntityObject(value) && processed.add(value)) {
                    doFill(value, sqlCommandType);
                }
            }
        } else {
            // 处理单个对象参数
            doFill(parameter, sqlCommandType);
        }
    }

    /**
     * 执行字段填充
     */
    private void doFill(Object entity, SqlCommandType sqlCommandType) {
        LocalDateTime now = LocalDateTime.now();
        
        if (sqlCommandType == SqlCommandType.INSERT) {
            // INSERT：填充createTime和updateTime（只在null时填充）
            setField(entity, CREATE_TIME, now, true);
            setField(entity, UPDATE_TIME, now, true);
        } else {
            // UPDATE：强制更新updateTime
            setField(entity, UPDATE_TIME, now, false);
        }
        
        log.debug("自动填充时间字段：{} [{}]", entity.getClass().getSimpleName(), sqlCommandType);
    }

    /**
     * 设置字段值
     */
    private void setField(Object object, String fieldName, Object value, boolean checkNull) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            
            if (!checkNull || field.get(object) == null) {
                field.set(object, value);
            }
        } catch (NoSuchFieldException e) {
            // 字段不存在，忽略（可能某些实体没有时间字段）
        } catch (Exception e) {
            log.warn("设置字段 {} 失败：{}", fieldName, e.getMessage());
        }
    }

    /**
     * 判断是否为实体对象
     */
    private boolean isEntityObject(Object obj) {
        if (obj == null) {
            return false;
        }
        String className = obj.getClass().getName();
        return !className.startsWith("java.") && !className.startsWith("javax.");
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }
}

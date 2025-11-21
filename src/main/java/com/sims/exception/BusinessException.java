package com.sims.exception;

import lombok.Getter;

/**
 * 自定义业务异常.
 * @author Administrator
 */
@Getter
public class BusinessException extends RuntimeException {

    private final Integer code;

    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
    }

}
package com.sims.result;

import lombok.*;

import java.io.Serializable;

/**
 * 统一接口返回结果.
 *
 * @author Administrator
 * @param <T> 数据泛型
 */
@Data
public class Result<T> implements Serializable {

    //状态码
    private Integer code;
    //错误信息
    private String msg;
    //数据
    private T data;

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<T>();
        result.code = 200;
        result.msg = "操作成功";
        result.data = data;
        return result;
    }

    public static <T> Result<T> success(String msg,T data) {
        Result<T> result = new Result<T>();
        result.code = 200;
        result.msg = msg;
        result.data = data;
        return result;
    }

    public static <T> Result<T> error(Integer code,String msg) {
        Result<T> result = new Result<T>();
        result.code = code;
        result.msg = msg;
        return result;
    }

}


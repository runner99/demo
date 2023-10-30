package com.runner99.serverDemo.common;

import lombok.Data;

/**
 * @author weizhenqiang
 * @date 2023/10/10 14:12
 */
@Data
public class Result<T> {
    private boolean success;

    private int code;

    private String msg;

    private T data;

    public Result(boolean success, int code, String msg, T data) {
        this.success = success;
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
    public static <T> Result<T> ofSuccess(T data){
        return new Result<>(true,0,"success",data);
    }
    public static <T> Result<T> ofFailed(String msg){
        return new Result<>(false,0,msg,null);
    }

    public static <T> Result<T> ofFailed(int code, String msg){
        return new Result<>(false,code,msg,null);
    }

}

package com.runner99.sql.common;


import lombok.Data;

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

    public static <T> Result<T> success(Object data){
        return new Result(true,200,"success",data);
    }

   public static <T> Result<T> fail(String msg){
        return new Result(false,999,msg,null);
    }

    public boolean isSuccess() {
        return success;
    }

}

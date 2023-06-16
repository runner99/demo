package com.runner.testworks.config;


import lombok.Data;

@Data
public class Result {
    private boolean success;

    private int code;

    private String msg;

    private Object data;

    public Result(boolean success, int code, String msg, Object data) {
        this.success = success;
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static Result success(Object data){
        return new Result(true,200,"success",data);
    }

   public static Result fail(String msg){
        return new Result(false,999,msg,null);
    }

    public boolean isSuccess() {
        return success;
    }

}

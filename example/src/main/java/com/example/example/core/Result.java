package com.example.example.core;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class Result<T> {
    static int SUCCESS_CODE=0;
    static int ERROR_CODE=-1;
    static int ERROR_CODE_USERNAME_NOT_EXIST=-2;
    static int ERROR_CODE_PASSWORD_WRONG=-3;
    static int ERROR_CODE_USERNAME_ALREADY_EXIST=-4;
    static int ERROR_CODE_NOT_SELECT=-5;
    static int ERROR_CODE_USERNAME_NOT_EXIST_2=-6;
    static int ERROR_CODE_PATH_NOT_EXIST_2=-7;


    // 响应代码
    private int code;
    private T data;
    private String msg = "ok";
    // 为了不让使用无参构造
    private Result(){}

    // 0000 0001 0002
    public Result(int code, T data, String msg){
        this.code=code;
        this.data=data;
        this.msg=msg;
    }

    public static Result success() {
        return new Result<>(SUCCESS_CODE, true, "ok");
    }
    public static Result fail() {
        return new Result<>(ERROR_CODE,false, "系统错误");
    }
    public static Result fail(String msg) {
        return new Result<>(ERROR_CODE,false, msg);
    }
    public static Result fail(int code,String msg) {
        return new Result<>(code,false, msg);
    }
    public static <T> Result<T> success(T data) {
        return new Result<>(SUCCESS_CODE, data, "ok");
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}

package com.sk.blog.utils;

public class Result {
    boolean success;
    String msg;

    public Result(String msg) {
        this.msg = msg;
    }
    public Result() {
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Result(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static Result ok()

    {
        return new Result(true);
    }

    public static Result msg(String msg)
    {
        return new Result(msg);

    }
}

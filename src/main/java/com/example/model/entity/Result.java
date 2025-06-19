package com.example.model.entity;

import java.util.Map;

public class Result {
    private Integer code;
    private String msg;
    private Object data;

    public Result(Integer code, String msg, Map<String,Object>  data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Result() {

    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(Object  data) {
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public Object  getData() {
        return data;
    }
}

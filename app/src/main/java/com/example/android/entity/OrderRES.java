package com.example.android.entity;

import java.util.List;

public class OrderRES {
    private Integer code;
    private String msg;
    private List<Order> data;

    public OrderRES(Integer code, String msg, List<Order> data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<Order> getData() {
        return data;
    }

    public void setData(List<Order> data) {
        this.data = data;
    }
}



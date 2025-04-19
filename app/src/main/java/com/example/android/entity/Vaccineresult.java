package com.example.android.entity;

public class Vaccineresult {
    Integer code;
    String msg;
    Vaccine data;

    public Vaccineresult(Integer code, String msg, Vaccine data) {
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

    public Vaccine getData() {
        return data;
    }

    public void setData(Vaccine data) {
        this.data = data;
    }
}

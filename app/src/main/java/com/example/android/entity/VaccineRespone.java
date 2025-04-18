package com.example.android.entity;

import java.util.List;

public class VaccineRespone {
    private String code;
    private String msg;

    private List<Vaccine> data;

    public VaccineRespone(){

    }

    public VaccineRespone(String code, String msg, List<Vaccine> data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<Vaccine> getData() {
        return data;
    }

    public void setData(List<Vaccine> data) {
        this.data = data;
    }
}

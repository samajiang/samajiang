package com.example.exception;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
public class CustomException extends RuntimeException{

    private String msg;
    public CustomException(String msg){
        this.msg=msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


}

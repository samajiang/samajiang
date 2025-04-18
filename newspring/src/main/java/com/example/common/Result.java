package com.example.common;

import lombok.Data;
import lombok.ToString;


public class Result {
//    定义返回码
    private static final String SUCCESS = "0";
    private static final String ERROR = "-1";

//    状态码，查看接口调用情况
    private String code;
//    如果报错把信息传到前端显示
    private String msg;
//    前后端的数据
    private Object data;

//    定义返回成功的方法
    public static Result success(){
        Result result = new Result();
        result.setCode(SUCCESS);
        return result;
    }

//    返回成功并且要获取数据的方法
    public static Result success(Object data){
        Result result = new Result();
        result.setCode(SUCCESS);
        result.setData(data);
        return result;
    }

//    返回失败的方法
    public static Result error(String msg){
        Result result = new Result();
        result.setCode(ERROR);
        result.setMsg(msg);
        return result;
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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}

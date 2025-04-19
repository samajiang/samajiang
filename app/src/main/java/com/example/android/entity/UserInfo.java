package com.example.android.entity;

import java.io.Serializable;

public class UserInfo implements Serializable {

    private int user_id;
    private String user_name;
    private String password;
    private String phone;

    private String age;
    private String sex;
    private String avatar_url;
    private String root;

    public UserInfo(){

    }

    public UserInfo(int user_id, String user_name, String password, String phone, String age, String sex, String avatar_url, String root) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.password = password;
        this.phone = phone;
        this.age = age;
        this.sex = sex;
        this.avatar_url = avatar_url;
        this.root = root;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }
}

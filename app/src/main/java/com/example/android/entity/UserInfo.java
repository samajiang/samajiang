package com.example.android.entity;

import java.io.Serializable;

public class UserInfo implements Serializable {

    private int user_id;
    private String username;
    private String password;
    private String phonenumber;

    public UserInfo(){

    }


    public UserInfo(int user_id, String username, String password, String phonenumber) {
        this.user_id = user_id;
        this.username = username;
        this.password = password;
        this.phonenumber = phonenumber;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }
}

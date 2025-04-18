package com.example.android.tool;

public class BASEURL {
    private static String Base_URL=new String("http://192.168.1.101:8080/api/");
    private static String touxiangurl = new String("http://192.168.1.101:8080");
    public static String getBase_URL(){
        return Base_URL;
    }
    public static String getTouxiangurl(){
        return touxiangurl;
    }
}

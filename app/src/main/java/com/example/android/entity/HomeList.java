package com.example.android.entity;

public class HomeList {

    private int NewPic;

    private String Title;

    private String NewDetail;

    public HomeList(int newPic, String title, String newDetail) {
        NewPic = newPic;
        Title = title;
        NewDetail = newDetail;
    }

    public HomeList(){

    }

    public int getNewPic() {
        return NewPic;
    }

    public void setNewPic(int newPic) {
        NewPic = newPic;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getNewDetail() {
        return NewDetail;
    }

    public void setNewDetail(String newDetail) {
        NewDetail = newDetail;
    }
}

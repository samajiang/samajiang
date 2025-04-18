package com.example.android.entity;

import androidx.annotation.NonNull;

public class Vaccine {
private int vaccine_id;
    private String vaccine_name;
    private String vaccine_datil;
    private String bianhao;

    private String vaccine_pic;

    private Integer vaccine_count;

    public Vaccine(){

    }

    public Vaccine(int vaccine_id, String vaccine_name, String vaccine_datil, String bianhao, String vaccine_pic, Integer vaccine_count) {
        this.vaccine_id = vaccine_id;
        this.vaccine_name = vaccine_name;
        this.vaccine_datil = vaccine_datil;
        this.bianhao = bianhao;
        this.vaccine_pic = vaccine_pic;
        this.vaccine_count = vaccine_count;
    }

    public int getVaccine_id() {
        return vaccine_id;
    }

    public void setVaccine_id(int vaccine_id) {
        this.vaccine_id = vaccine_id;
    }

    public String getVaccine_name() {
        return vaccine_name;
    }

    public void setVaccine_name(String vaccine_name) {
        this.vaccine_name = vaccine_name;
    }

    public String getVaccine_datil() {
        return vaccine_datil;
    }

    public void setVaccine_datil(String vaccine_datil) {
        this.vaccine_datil = vaccine_datil;
    }

    public String getBianhao() {
        return bianhao;
    }

    public void setBianhao(String bianhao) {
        this.bianhao = bianhao;
    }

    public String getVaccine_pic() {
        return vaccine_pic;
    }

    public void setVaccine_pic(String vaccine_pic) {
        this.vaccine_pic = vaccine_pic;
    }

    public Integer getVaccine_count() {
        return vaccine_count;
    }

    public void setVaccine_count(Integer vaccine_count) {
        this.vaccine_count = vaccine_count;
    }

    @NonNull
    @Override
    public String toString() {
        return "Vaccine{" +
                "vaccine_id=" + vaccine_id +
                ", vaccine_name='" + vaccine_name + '\'' +
                ", vaccine_datil='" + vaccine_datil + '\'' +
                ", bianhao='" + bianhao + '\'' +
                ", vaccine_pic='" + vaccine_pic + '\'' +
                '}';
    }
}

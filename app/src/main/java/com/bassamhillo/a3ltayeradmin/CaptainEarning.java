package com.bassamhillo.a3ltayeradmin;

public class CaptainEarning {
    private String name;
    private String country;
    private String city;
    private String mobile;

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    private String uid;
    private int earning;

    public void setName(String name) {
        this.name = name;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setEarning(int earning) {
        this.earning = earning;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getMobile() {
        return mobile;
    }

    public int getEarning() {
        return earning;
    }

    public CaptainEarning(String name, String country, String city, String mobile, int earning,String uid) {
        this.name = name;
        this.country = country;
        this.city = city;
        this.mobile = mobile;
        this.earning = earning;
        this.uid=uid;
    }
}

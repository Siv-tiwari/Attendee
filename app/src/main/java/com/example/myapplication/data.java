package com.example.myapplication;

public class data {
    private int id;
    private String full_name;
    private String user_name;
    private String sap;
    private String roll_no;
    private String password;

    public data(String full_name, String user_name, String sap, String roll_no, String password) {
        this.full_name = full_name;
        this.user_name = user_name;
        this.sap = sap;
        this.roll_no = roll_no;
        this.password = password;
    }
    public data(int id,String full_name, String user_name, String sap, String roll_no, String password) {
        this.id = id;
        this.full_name = full_name;
        this.user_name = user_name;
        this.sap = sap;
        this.roll_no = roll_no;
        this.password = password;
    }
    public data(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getSap() {
        return sap;
    }

    public void setSap(String sap) {
        this.sap = sap;
    }

    public String getRoll_no() {
        return roll_no;
    }

    public void setRoll_no(String roll_no) {
        this.roll_no = roll_no;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}

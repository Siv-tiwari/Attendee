package com.example.myapplication;

public class attendence_modle {
    public String date;
    public String status;
    public String user_name;
    public String user_sap;
    public boolean check_box;

    public attendence_modle(String date, String status) {
        this.date = date;
        this.status = status;
    }

    public attendence_modle(String user_name,String user_sap,boolean check_box){
        this.user_name = user_name;
        this.user_sap = user_sap;
        this.check_box = check_box;
    }


    public boolean getCheck_box() {
        return check_box;
    }

    public void setCheck_box(boolean check_box) {
        this.check_box = check_box;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_sap() {
        return user_sap;
    }

    public void setUser_sap(String user_sap) {
        this.user_sap = user_sap;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}

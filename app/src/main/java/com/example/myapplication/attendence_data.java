package com.example.myapplication;

public class attendence_data {
    private int id;
    public String sap;
    public String date;
    public int present;
    public int absent;
    public int leave;

    public attendence_data(int id, String sap, String date, int present, int absent, int leave) {
        this.id = id;
        this.sap = sap;
        this.date = date;
        this.present = present;
        this.absent = absent;
        this.leave = leave;
    }

    public attendence_data(String sap, String date, int present, int absent, int leave) {
        this.sap = sap;
        this.date = date;
        this.present = present;
        this.absent = absent;
        this.leave = leave;
    }

    public attendence_data() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSap() {
        return sap;
    }

    public void setSap(String sap) {
        this.sap = sap;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getPresent() {
        return present;
    }

    public void setPresent(int present) {
        this.present = present;
    }

    public int getAbsent() {
        return absent;
    }

    public void setAbsent(int absent) {
        this.absent = absent;
    }

    public int getLeave() {
        return leave;
    }

    public void setLeave(int leave) {
        this.leave = leave;
    }
}

package com.mgUnicorn.eh.models;

public class opdModel {

    String date,name,number;

    opdModel(){}
    public opdModel(String date, String name, String number) {
        this.date = date;
        this.name = name;
        this.number = number;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}

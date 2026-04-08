package com.mgUnicorn.eh.models;

public class patientModel {

    String imageUrl,name,number,date;
    String b,c; // Firebase field names

    // Default constructor required for Firebase
    public patientModel(){}


    public patientModel(String imageUrl, String name, String number, String date) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.number = number;
        this.date = date;

    }

    public patientModel( String name, String number, String date) {

        this.name = name;
        this.number = number;
        this.date = date;

    }



    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    // Getters and setters for Firebase field names
    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }
}

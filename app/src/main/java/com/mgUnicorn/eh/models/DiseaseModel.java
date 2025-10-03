package com.mgUnicorn.eh.models;

public class DiseaseModel {

    String imageUrl,name,treatment;
    String a,b; // Firebase field names

    DiseaseModel(){

    }

    public DiseaseModel(String imageUrl, String name, String treatment) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.treatment = treatment;
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

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    // Getters and setters for Firebase field names
    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }
}

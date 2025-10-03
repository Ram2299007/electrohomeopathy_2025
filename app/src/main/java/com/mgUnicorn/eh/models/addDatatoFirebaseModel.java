package com.mgUnicorn.eh.models;

public class addDatatoFirebaseModel {

    String name,treatment;


     addDatatoFirebaseModel() {
    }

    public addDatatoFirebaseModel(String name, String treatment) {
        this.name = name;
        this.treatment = treatment;
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
}

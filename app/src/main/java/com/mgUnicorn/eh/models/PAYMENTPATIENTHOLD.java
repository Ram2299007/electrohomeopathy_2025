package com.mgUnicorn.eh.models;

public class PAYMENTPATIENTHOLD {
    String payment,address,occupation,age,sex,bp,weight,temperament,chiefcomplaints,clinicalfeature,diagnosis,investigation,advice,treatment;

    PAYMENTPATIENTHOLD(){}


    public PAYMENTPATIENTHOLD(String payment, String address, String occupation, String age, String bp, String weight, String chiefcomplaints
  , String clinicalfeature ,String diagnosis,String  investigation,String advice,String treatment) {
        this.payment = payment;
        this.address = address;
        this.occupation = occupation;
        this.age = age;

        this.bp = bp;
        this.weight = weight;
        this.chiefcomplaints=chiefcomplaints;
        this.diagnosis=diagnosis;
        this.investigation=investigation;
        this.advice=advice;
        this.treatment=treatment;
        this.clinicalfeature=clinicalfeature;

    }

    public String getClinicalfeature() {
        return clinicalfeature;
    }

    public void setClinicalfeature(String clinicalfeature) {
        this.clinicalfeature = clinicalfeature;
    }

    public String getChiefcomplaints() {
        return chiefcomplaints;
    }

    public void setChiefcomplaints(String chiefcomplaints) {
        this.chiefcomplaints = chiefcomplaints;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getInvestigation() {
        return investigation;
    }

    public void setInvestigation(String investigation) {
        this.investigation = investigation;
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBp() {
        return bp;
    }

    public void setBp(String bp) {
        this.bp = bp;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getTemperament() {
        return temperament;
    }

    public void setTemperament(String temperament) {
        this.temperament = temperament;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }
}

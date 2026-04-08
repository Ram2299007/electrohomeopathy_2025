package com.mgUnicorn.eh.models;

public class DiscoveryModel {

    String Present_Complaints,Diagnosis,Investigation,Treatment;

   public DiscoveryModel(){

        //Require Empty constructor
    }

    public DiscoveryModel(String present_Complaints, String diagnosis, String investigation, String treatment) {
        Present_Complaints = present_Complaints;
        Diagnosis = diagnosis;
        Investigation = investigation;
        Treatment = treatment;
    }


    public String getPresent_Complaints() {
        return Present_Complaints;
    }

    public void setPresent_Complaints(String present_Complaints) {
        Present_Complaints = present_Complaints;
    }

    public String getDiagnosis() {
        return Diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        Diagnosis = diagnosis;
    }

    public String getInvestigation() {
        return Investigation;
    }

    public void setInvestigation(String investigation) {
        Investigation = investigation;
    }

    public String getTreatment() {
        return Treatment;
    }

    public void setTreatment(String treatment) {
        Treatment = treatment;
    }
}

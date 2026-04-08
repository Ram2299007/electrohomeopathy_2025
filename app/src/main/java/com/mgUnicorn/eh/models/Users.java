package com.mgUnicorn.eh.models;

public class Users {

    String profilepic,userName,mail,password,userId,lastmessage,status,header,clinic,degree,reg,Present_Complaints,Diagnosis,Investigation,Treatment,addressDoctor,address;


    public Users(String profilepic, String userName, String mail, String password, String userId, String lastmessage, String status, String header, String clinic, String degree, String reg, String present_Complaints, String diagnosis, String investigation, String treatment,String addressDoctor,String addrss) {
        this.profilepic = profilepic;
        this.userName = userName;
        this.mail = mail;
        this.password = password;
        this.userId = userId;
        this.lastmessage = lastmessage;
        this.status = status;
        this.header = header;
        this.clinic = clinic;
        this.degree = degree;
        this.reg = reg;
        this.Present_Complaints = present_Complaints;
        this.Diagnosis = diagnosis;
        this.Investigation = investigation;
        this.Treatment = treatment;
        this.addressDoctor=addressDoctor;
        this.address=addrss;
    }

    public Users(){}

    //sign up constructor

    //when error check here double constructor created

    public Users( String userName, String mail, String password) {
        this.userName = userName;
        this.mail = mail;
        this.password = password;


    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddressDoctor() {
        return addressDoctor;
    }

    public void setAddressDoctor(String addressDoctor) {
        this.addressDoctor = addressDoctor;
    }

    public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLastmessage() {
        return lastmessage;
    }

    public void setLastmessage(String lastmessage) {
        this.lastmessage = lastmessage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getClinic() {
        return clinic;
    }

    public void setClinic(String clinic) {
        this.clinic = clinic;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getReg() {
        return reg;
    }

    public void setReg(String reg) {
        this.reg = reg;
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

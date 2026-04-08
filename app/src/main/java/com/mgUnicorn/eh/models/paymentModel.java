package com.mgUnicorn.eh.models;

public class paymentModel {


    String paymentMessage;


    public paymentModel(){}

    public paymentModel(String paymentMessage) {
        this.paymentMessage = paymentMessage;
    }


    public String getPaymentMessage() {
        return paymentMessage;
    }

    public void setPaymentMessage(String paymentMessage) {
        this.paymentMessage = paymentMessage;
    }
}

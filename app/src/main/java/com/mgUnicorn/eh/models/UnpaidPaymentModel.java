package com.mgUnicorn.eh.models;

public class UnpaidPaymentModel {

    String unpaid;


    //empty constructor require
    public UnpaidPaymentModel(){}

    public UnpaidPaymentModel(String unpaid) {
        this.unpaid = unpaid;
    }

    public String getUnpaid() {
        return unpaid;
    }

    public void setUnpaid(String unpaid) {
        this.unpaid = unpaid;
    }
}

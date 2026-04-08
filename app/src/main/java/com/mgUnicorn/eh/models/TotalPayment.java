package com.mgUnicorn.eh.models;

public class TotalPayment {

    String unpaid,TotalPaid,paid;

    public TotalPayment(){}


    public TotalPayment(String unpaid, String totalPaid, String paid) {
        this.unpaid = unpaid;
        TotalPaid = totalPaid;
        this.paid = paid;
    }

    public String getUnpaid() {
        return unpaid;
    }

    public void setUnpaid(String unpaid) {
        this.unpaid = unpaid;
    }

    public String getTotalPaid() {
        return TotalPaid;
    }

    public void setTotalPaid(String totalPaid) {
        TotalPaid = totalPaid;
    }

    public String getPaid() {
        return paid;
    }

    public void setPaid(String paid) {
        this.paid = paid;
    }
}

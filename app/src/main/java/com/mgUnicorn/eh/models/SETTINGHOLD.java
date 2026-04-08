package com.mgUnicorn.eh.models;

public class SETTINGHOLD {

    String a,b,c,d,e;

     SETTINGHOLD() {
    }

    public SETTINGHOLD(String a, String b, String c, String d, String e) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.e = e;
    }


    public String geta() {
        return a;
    }

    public void seta(String a) {
        this.a = a;
    }

    public String getb() {
        return b;
    }

    public void setb(String b) {
        this.b = b;
    }

    public String getc() {
        return c;
    }

    public void setc(String c) {
        this.c = c;
    }

    public String getd() {
        return d;
    }

    public void setd(String d) {
        this.d = d;
    }

    public String gete() {
        return e;
    }

    public void sete(String e) {
        this.e = e;
    }
}

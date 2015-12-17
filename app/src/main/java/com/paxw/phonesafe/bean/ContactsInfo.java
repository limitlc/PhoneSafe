package com.paxw.phonesafe.bean;

/**
 * Created by lichuang on 2015/12/17.
 */
public class ContactsInfo {
    private String number;
    private String mode;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    @Override
    public String toString() {
        return "BlackNumberInfo [number=" + number + ", mode=" + mode + "]";
    }
}

package com.example.petfeeder;

public class Data {

    String date;
    String time;
    String lvl1;
    String lvl2;
    String lvl3;
    String lvl4;
    String lvl5;
    String lvl6;

    public Data(String date, String time, String lvl1, String lvl2, String lvl3, String lvl4, String lvl5, String lvl6) {
        this.date = date;
        this.time = time;
        this.lvl1 = lvl1;
        this.lvl2 = lvl2;
        this.lvl3 = lvl3;
        this.lvl4 = lvl4;
        this.lvl5 = lvl5;
        this.lvl6 = lvl6;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getLvl1() {
        return lvl1;
    }

    public String getLvl2() {
        return lvl2;
    }

    public String getLvl3() {
        return lvl3;
    }

    public String getLvl4() {
        return lvl4;
    }

    public String getLvl5() {
        return lvl5;
    }

    public String getLvl6() {
        return lvl6;
    }
}
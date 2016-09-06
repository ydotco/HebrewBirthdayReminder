package com.ydotco.hebrewbirthdayreminder;

import java.util.Date;

/**
 * Created by yotamc on 09-Aug-16.
 */
public class HebEngDate {

    /**
     * gy : 1989
     * gm : 4
     * gd : 27
     * hy : 5749
     * hm : Nisan
     * hd : 22
     * hebrew : כ״ב בְּנִיסָן תשמ״ט
     * events : ["Parashat Achrei Mot","Pesach VIII","7th day of the Omer"]
     */

    private int gy;
    private int gm;
    private int gd;
    private int hy;
    private String hm;
    private int hd;

    @Override
    public String toString() {
        return "HebEngDate{" +
                "gy=" + gy +
                ", gm=" + gm +
                ", gd=" + gd +
                ", hy=" + hy +
                ", hm='" + hm + '\'' +
                ", hd=" + hd +
                '}';
    }

    //getters and setters
    public int getGy() {
        return gy;
    }
    public void setGy(int gy) {
        this.gy = gy;
    }
    public int getGm() {
        return gm;
    }
    public void setGm(int gm) {
        this.gm = gm;
    }
    public int getGd() {
        return gd;
    }
    public void setGd(int gd) {
        this.gd = gd;
    }
    public int getHy() {
        return hy;
    }
    public void setHy(int hy) {
        this.hy = hy;
    }
    public String getHm() {
        return hm;
    }
    public void setHm(String hm) {
        this.hm = hm;
    }
    public int getHd() {
        return hd;
    }
    public void setHd(int hd) {
        this.hd = hd;
    }

    //methods

    public Date makeDate(){

        String mgd ;
        String mgm = Util.ConvertIntToString(gm);
        String mgy= Util.ConvertIntToString(gy);
        if (gd<10)
            mgd= "0"+Util.ConvertIntToString(gd);
        else
            mgd=Util.ConvertIntToString(gd);
        String s= mgd +"/"+mgm +"/"+ mgy;
            return Util.CovertStringToDate(s);
    }
}

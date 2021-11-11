package com.tr.blebutton;

public class NormalUser {
    String key;
    String UID;
    String Index;
    String DaireNo;
    String BlokAdi;
    String Tamisim;

    public NormalUser(){

    }
    public NormalUser(String key, String UID, String index, String daireNo, String blokAdi, String tamisim) {
        this.key = key;
        this.UID = UID;
        this.Index = index;
        this.DaireNo = daireNo;
        this.BlokAdi = blokAdi;
        this.Tamisim = tamisim;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getIndex() {
        return Index;
    }

    public void setIndex(String index) {
        Index = index;
    }

    public String getDaireNo() {
        return DaireNo;
    }

    public void setDaireNo(String daireNo) {
        DaireNo = daireNo;
    }

    public String getBlokAdi() {
        return BlokAdi;
    }

    public void setBlokAdi(String blokAdi) {
        BlokAdi = blokAdi;
    }

    public String getTamisim() {
        return Tamisim;
    }

    public void setTamisim(String tamisim) {
        Tamisim = tamisim;
    }
}

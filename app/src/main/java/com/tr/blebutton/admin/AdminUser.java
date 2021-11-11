package com.tr.blebutton.admin;

public class AdminUser {

    private String email;
    private String pass;
    private String key;
    private String keyIndex;
    private String UI;
    public AdminUser(){

    }

    public AdminUser(String email, String pass, String key, String keyIndex, String UI) {
        this.email = email;
        this.pass = pass;
        this.key = key;
        this.keyIndex = keyIndex;
        this.UI = UI;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKeyIndex() {
        return keyIndex;
    }

    public void setKeyIndex(String keyIndex) {
        this.keyIndex = keyIndex;
    }

    public String getUI() {
        return UI;
    }

    public void setUI(String UI) {
        this.UI = UI;
    }
}

package com.example.fotogallery.model;

public class KullaniciBilgi {


    private static KullaniciBilgi kullaniciBilgi;

    private String userName;
    private String email;
    private String password;

    private KullaniciBilgi() {}

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static KullaniciBilgi getInstance() {

        if(kullaniciBilgi == null) {
            kullaniciBilgi = new KullaniciBilgi();
        }
        return kullaniciBilgi;

    }


}

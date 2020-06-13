package com.example.tugbes.models;

public class Users {
    private String nama, nohp, password, image, alamat;

    public Users() {

    }
    public Users(String nama, String nohp, String password, String image, String alamat) {
        this.nama = nama;
        this.nohp = nohp;
        this.password = password;
        this.image = image;
        this.alamat = alamat;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNohp() {
        return nohp;
    }

    public void setNohp(String nohp) {
        this.nohp = nohp;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
}

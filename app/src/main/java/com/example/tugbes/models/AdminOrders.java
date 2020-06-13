package com.example.tugbes.models;

public class AdminOrders {
    private String nama, nohp, alamat, kota, status, date, time, totalAmount;

    public AdminOrders() {
    }

    public AdminOrders(String nama, String nohp, String alamat, String kota, String status, String date, String time, String totalAmount) {
        this.nama = nama;
        this.nohp = nohp;
        this.alamat = alamat;
        this.kota = kota;
        this.status = status;
        this.date = date;
        this.time = time;
        this.totalAmount = totalAmount;
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

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getKota() {
        return kota;
    }

    public void setKota(String kota) {
        this.kota = kota;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }
}

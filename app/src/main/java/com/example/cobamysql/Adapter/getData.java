package com.example.cobamysql.Adapter;

public class getData {
    String nama="";
    String alamat="";
    String no_telp="";

    public getData(String nama, String alamat, String no_telp){
        this.nama = nama;
        this.alamat = alamat;
        this.no_telp = no_telp;
    }

    public String getNama() {
        return nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getNo_telp() {
        return no_telp;
    }


}

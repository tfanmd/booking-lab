package com.example.cobamysql.Adapter;

public class getData {
    private String inisial = "";
    private String nama = "";
    private String alamat = "";
    private String no_telp = "";
    private String id_mahasiswa;




    public getData(String inisial, String id_mahasiswa,String nama, String alamat, String no_telp){
        this.inisial = inisial;
        this.id_mahasiswa = id_mahasiswa;
        this.nama = nama;
        this.alamat = alamat;
        this.no_telp = no_telp;
    }
    public String getInisial() {
        return inisial;
    }
    public String getNama() {
        return nama;
    }

    public String getId_mahasiswa() {
        return id_mahasiswa;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getNo_telp() {
        return no_telp;
    }


}

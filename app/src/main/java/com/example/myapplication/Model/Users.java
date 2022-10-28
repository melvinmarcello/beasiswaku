package com.example.myapplication.Model;

public class Users {
    private String age, alamat, email, id, nama, phone, profImage, major;

    public Users(String age, String alamat, String email, String id, String nama, String phone, String profImage, String major) {
        this.age = age;
        this.alamat = alamat;
        this.email = email;
        this.id = id;
        this.nama = nama;
        this.phone = phone;
        this.profImage = profImage;
        this.major = major;
    }
    public Users(){

    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProfImage() {
        return profImage;
    }

    public void setProfImage(String profImage) {
        this.profImage = profImage;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }
}

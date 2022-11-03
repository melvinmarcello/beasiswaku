package com.example.myapplication.Model;

public class Scholarship {
    private String desc, title, img_url, id;

    public Scholarship(String desc, String title, String img_url, String id) {
        this.id = id;
        this.desc = desc;
        this.title = title;
        this.img_url = img_url;
    }
    public Scholarship(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getDesc() {
        return desc;
    }

    public String getTitle() {
        return title;
    }

    public String getImg_url() {
        return img_url;
    }
}

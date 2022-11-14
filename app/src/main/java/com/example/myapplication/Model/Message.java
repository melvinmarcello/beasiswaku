package com.example.myapplication.Model;

public class Message {
    String message, key, name, imgProf;

    public Message(){

    }

    public Message(String message, String name, String imgProf) {
        this.message = message;
        this.name = name;
        this.key = key;
        this.imgProf = imgProf;
    }

    public String getImgProf() {
        return imgProf;
    }

    public void setImgProf(String imgProf) {
        this.imgProf = imgProf;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "Message{" +
                "message='" + message + '\'' +
                ", name='" + name + '\'' +
                ", key='" + key + '\'' +
                '}';
    }
}

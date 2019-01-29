package com.androidtan.www.aplikasi_patrol.Models;

import com.google.gson.annotations.SerializedName;

public class UserModel {

    @SerializedName("message")
    private String message;

    @SerializedName("success")
    private boolean success;

    @SerializedName("id")
    private int id;

    @SerializedName("nama")
    private String nama;

    @SerializedName("password")
    private boolean password;

    public void setMsg(String message){
        this.message = message;
    }

    public String getMsg(){
        return message;
    }

    public void setSuccess(boolean success){
        this.success = success;
    }

    public boolean getSuccess(){
        return success;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }

    public void setNam(String nama){
        this.nama = nama;
    }

    public String getNama(){
        return nama;
    }

    public void setPassword(boolean password) {
        this.password=password;
    }

    public boolean getPassword(){
        return  password;
    }

    @Override
    public String toString(){
        return
                "UserModel{" +
                        "message = '" + message + '\'' +
                        ",success = '" + success + '\'' +
                        ",id = '" + id + '\'' +
                        ",nama = '" + nama + '\'' +
                        ",password = '" + password + '\'' +
                        "}";    }

}

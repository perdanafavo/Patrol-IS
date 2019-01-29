package com.androidtan.www.aplikasi_patrol.Models;

import com.google.gson.annotations.SerializedName;

public class PresensiModel {

    @SerializedName("message")
    private String message;

    @SerializedName("success")
    private boolean success;

    @SerializedName("tanggal")
    private String tanggal;

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

    public void setTgl(String tanggal){
        this.tanggal = tanggal;
    }

    public String getTgl(){
        return tanggal;
    }

    @Override
    public String toString(){
        return
                "PresensiModel{" +
                        "message = '" + message + '\'' +
                        ",success = '" + success + '\'' +
                        "}";    }

}

package com.androidtan.www.aplikasi_patrol.Models;

import com.google.gson.annotations.SerializedName;

public class LogModel {
    private String ID_ACT;
    private String ID_PATROL;
    private String KETERANGAN;
    private String WAKTU;
    private String TANGGAL;

    @SerializedName("success")
    private boolean success = true;

    public void setID_ACT(String ID_ACT) {
        this.ID_ACT = ID_ACT;
    }

    public String getID_ACT() {
        return ID_ACT;
    }

    public void setID_PATROL(String ID_PATROL) {
        this.ID_PATROL = ID_PATROL;
    }

    public String getID_PATROL() {
        return ID_PATROL;
    }

    public String getKETERANGAN() {
        return KETERANGAN;
    }

    public String getTANGGAL() {
        return TANGGAL;
    }

    public String getWAKTU() {
        return WAKTU;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}

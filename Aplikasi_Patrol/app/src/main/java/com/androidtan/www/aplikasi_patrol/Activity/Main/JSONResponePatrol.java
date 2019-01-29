package com.androidtan.www.aplikasi_patrol.Activity.Main;


import com.androidtan.www.aplikasi_patrol.Models.LogModel;
import com.androidtan.www.aplikasi_patrol.Models.PatrolModel;

public class JSONResponePatrol {
    private PatrolModel[] patrol;

    private LogModel[] log;

    public LogModel[] getLog() { return log;   }

    public PatrolModel[] getPatrol() {
        return patrol;
    }
}

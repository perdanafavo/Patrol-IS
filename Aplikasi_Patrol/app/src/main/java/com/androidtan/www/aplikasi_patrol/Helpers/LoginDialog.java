package com.androidtan.www.aplikasi_patrol.Helpers;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import android.support.v7.app.AppCompatDialogFragment;

public class LoginDialog extends AppCompatDialogFragment {
    //status
    //0=username salah
    //1=username kosong
    //2=password kosong
    //3=presensi
    private static int status;
    private static String waktu,tanggal;

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        if (status==0) {
            builder.setTitle("Information")
                    .setMessage("Username tidak terdaftar")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
        }
        else if(status==1){
            builder.setTitle("Information")
                    .setMessage("Username tidak boleh kosong")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
        }
        else if(status==2){
            builder.setTitle("Information")
                    .setMessage("Password tidak boleh kosong")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
        }
        else if (status==3){
            builder.setTitle("Information")
                    .setMessage("Anda hadir pada tanggal "+tanggal+" pukul "+waktu)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
        }
        return builder.create();
    }
    public static void paketLogin(int status){
        LoginDialog.status=status;
    }
}
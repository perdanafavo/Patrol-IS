package com.androidtan.www.aplikasi_patrol.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.androidtan.www.aplikasi_patrol.Helpers.SharedPrefManager;
import com.androidtan.www.aplikasi_patrol.Models.PresensiModel;
import com.androidtan.www.aplikasi_patrol.Network.ApiServices;
import com.androidtan.www.aplikasi_patrol.Network.InitRetrofit;
import com.androidtan.www.aplikasi_patrol.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PresensiActivity extends AppCompatActivity {

    private static String nama, currentDate, time;
    private SharedPrefManager session;
    protected Context mContext;
    private Button btnHadir;
    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presensi);

        //inisialisasi widget pada layout
        TextView presenNama = findViewById(R.id.presenNama);
        btnHadir = findViewById(R.id.btnPresent);
        mDialog = new ProgressDialog(PresensiActivity.this);
        mDialog.setMessage("Please Waiting...");
        mDialog.setCancelable(false);
        mDialog.setIndeterminate(true);

        //inisialisaikan context
        mContext = this;

        //menampilkan nama
        session = new SharedPrefManager(this);
        nama = session.getSpFullname();
        presenNama.setText(nama);

        //menampilkan waktu kontinyu
        waktuTerus();

        //waktu untuk dialog
        setTime();

        cekAbsensi();


    }

    public void cekAbsensi() {
        mDialog.show();

        int id = session.getSpIduser();

        ApiServices api = InitRetrofit.getInstance();
        Call<PresensiModel> changeCall = api.getAbsensi(id);
        changeCall.enqueue(new Callback<PresensiModel>() {
            @Override
            public void onResponse(Call<PresensiModel> call, Response<PresensiModel> response) {
                if (response.isSuccessful()) {
                    boolean status = response.body().getSuccess();
                    if (status) {
                        String tanggal = response.body().getTgl();
                        if (tanggal.equals(currentDate)){
                            //DIALOG LOGIN
                            mDialog.dismiss();
                            new AlertDialog.Builder(PresensiActivity.this)
                                    .setTitle("Information")
                                    .setMessage("Anda telah Absen untuk hari ini")
                                    .setCancelable(false)
                                    .setPositiveButton("oke", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            finish();
                                        }
                                    }).show();
                            btnHadir.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    new AlertDialog.Builder(PresensiActivity.this)
                                            .setTitle("Information")
                                            .setMessage("Anda telah Absen untuk hari ini")
                                            .setCancelable(false)
                                            .setPositiveButton("oke", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    finish();
                                                }
                                            }).show();
                                }
                            });
                        } else {
                            mDialog.dismiss();
                            //menambahkan event
                            klikTombol();
                        }
                    } else {
                        mDialog.dismiss();
                        //menambahkan event
                        klikTombol();
                    }
                } else {
                    mDialog.dismiss();
                    //menambahkan event
                    klikTombol();
                }
            }
            @Override
            public void onFailure(Call<PresensiModel> call, Throwable t) {

            }
        });
    }

    public void setTime() {

        Calendar calendar = Calendar.getInstance();

        //menampilkan waktu
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        time = format.format(calendar.getTime());

        TextView txtTime = findViewById(R.id.presenJam);

        //menampilkan tanggal
        SimpleDateFormat format2 = new SimpleDateFormat("dd/MM/yyyy");
        currentDate = format2.format(calendar.getTime());

        TextView txtDate = findViewById(R.id.presenTgl);
    }

    public void waktuTerus(){
        Thread t = new Thread(){
        @Override
        public void run() {
            try {
                while (!isInterrupted()){
                    Thread.sleep(1000);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            TextView txtTime = findViewById(R.id.presenJam);
                            TextView txtDate = findViewById(R.id.presenTgl);
                            long date = System.currentTimeMillis();
                            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                            String timeString = sdf.format(date);
                            txtTime.setText(timeString);
                            SimpleDateFormat sdf2 = new SimpleDateFormat("dd MMM yyyy");
                            String dateString = sdf2.format(date);
                            txtDate.setText(dateString);
                        }
                    });
                }
            }catch (InterruptedException e){

            }
        }
        };
        t.start();
    }

    public void klikTombol(){
        btnHadir.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mDialog.show();
                int id = session.getSpIduser();

                ApiServices api = InitRetrofit.getInstance();
                Call<PresensiModel> changeCall = api.setAbsensi(id, currentDate, time);
                changeCall.enqueue(new Callback<PresensiModel>() {
                    @Override
                    public void onResponse(Call<PresensiModel> call, Response<PresensiModel> response) {
                        if (response.isSuccessful()) {
                            boolean status = response.body().getSuccess();
                            if (status) {
                                mDialog.dismiss();
                                //DIALOG LOGIN
                                new AlertDialog.Builder(PresensiActivity.this)
                                        .setTitle("Information")
                                        .setCancelable(false)
                                        .setMessage("Anda hadir pada tanggal "+currentDate+" pukul "+time)
                                        .setPositiveButton("oke", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                finish();
                                            }
                                        }).show();
                                btnHadir.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        cekAbsensi();
                                    }
                                });
                            } else {

                            }
                        } else {

                        }
                    }
                    @Override
                    public void onFailure(Call<PresensiModel> call, Throwable t) {

                    }
                });
            }
        });
    }

}


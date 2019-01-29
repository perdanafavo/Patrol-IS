package com.androidtan.www.aplikasi_patrol;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidtan.www.aplikasi_patrol.Activity.Main.JSONResponePatrol;
import com.androidtan.www.aplikasi_patrol.Activity.PresensiActivity;
import com.androidtan.www.aplikasi_patrol.Helpers.SharedPrefManager;
import com.androidtan.www.aplikasi_patrol.Models.PatrolModel;
import com.androidtan.www.aplikasi_patrol.Network.ApiServices;
import com.androidtan.www.aplikasi_patrol.Network.InitRetrofit;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideOption;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.signature.ObjectKey;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;

public class TestActivity extends AppCompatActivity {

    private int id;
    private ImageView imageView;
    private String dataLat,dataLng,namaGambar;
    private TextView txtLokasi;
    private Button btnCamera;
    //get from the DB
    private ArrayList<PatrolModel> patrol;
    private SharedPrefManager session;
    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        mDialog = new ProgressDialog(TestActivity.this);
        mDialog.setMessage("Please Waiting...");
        mDialog.setCancelable(false);
        mDialog.setIndeterminate(true);
        mDialog.show();

        //session
        session = new SharedPrefManager(this);

        id = getIntent().getIntExtra("id", 0);
        fetchData();
        kameraKlik();
    }

    private void kameraKlik() {
        btnCamera = (Button)findViewById(R.id.btnCamera);
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent move = new Intent(TestActivity.this, CameraActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                move.putExtra("id",id);
                move.putExtra("Bool", true);
                move.putExtra("namaGambar",namaGambar);
                finish();
                startActivity(move);
            }
        });
    }

    private void fetchData() {
        InitRetrofit.getInstance()
                .getPatrol(session.getSpIduser())
                .enqueue(new Callback<JSONResponePatrol>() {
            @Override
            public void onResponse(Call<JSONResponePatrol> call, retrofit2.Response<JSONResponePatrol> response) {
                JSONResponePatrol jsonResponePatrol = response.body();
                patrol = new ArrayList<>(Arrays.asList(jsonResponePatrol.getPatrol()));

                for (int i = 0; i < patrol.size(); i++) {
                    int namaID=patrol.get(i).getID_PATROL();
                    if(namaID==id){
                        namaGambar=patrol.get(i).getPICT();
                        dataLng=patrol.get(i).getLONGITUDE();
                        dataLat=patrol.get(i).getLATITUDE();
                        if (namaGambar == null){
                            Intent move = new Intent(TestActivity.this, CameraActivity.class)
                                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            move.putExtra("id",id);
                            move.putExtra("Bool", false);
                            finish();
                            startActivity(move);
                        }
                        fetchImage();
                    }
                }
            }

            @Override
            public void onFailure(Call<JSONResponePatrol> call, Throwable t) {
                Log.d("Error",t.getMessage());
            }
        });
    }

    private void fetchImage() {
        imageView = (ImageView)findViewById(R.id.imageView);

        txtLokasi=(TextView)findViewById(R.id.txtLokasi);
        //data yang akan ditampilkan
        String locDisplay="Latitude: "+dataLat+"\nLongitude: "+dataLng;
        txtLokasi.setText(locDisplay);

        RequestOptions requestOptions = new RequestOptions();

        Glide.with(TestActivity.this)
                .load("https://patrolis.000webhostapp.com/foto_patrolis/"+namaGambar)
                .apply(requestOptions.signature(new ObjectKey(String.valueOf(System.currentTimeMillis()))))
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        new AlertDialog.Builder(TestActivity.this)
                                .setTitle("Information")
                                .setMessage("Maaf ada kesalahan")
                                .setCancelable(false)
                                .setPositiveButton("oke", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        finish();
                                    }
                                }).show();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        mDialog.dismiss();
                        return false;
                    }
                })
                .into(imageView);
    }


}

package com.androidtan.www.aplikasi_patrol.Activity.Main;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.androidtan.www.aplikasi_patrol.Activity.LoginActivity;
import com.androidtan.www.aplikasi_patrol.Activity.PresensiActivity;
import com.androidtan.www.aplikasi_patrol.Adapter.PatrolAdapter;
import com.androidtan.www.aplikasi_patrol.Helpers.SharedPrefManager;
import com.androidtan.www.aplikasi_patrol.MapsActivity;
import com.androidtan.www.aplikasi_patrol.Models.LogModel;
import com.androidtan.www.aplikasi_patrol.Models.PatrolModel;
import com.androidtan.www.aplikasi_patrol.Network.ApiServices;
import com.androidtan.www.aplikasi_patrol.Network.InitRetrofit;
import com.androidtan.www.aplikasi_patrol.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MainActivity extends AppCompatActivity {

    private static String nama, time, currentDate;
    private static final int TIME_INTERVAL = 2000;
    private long mBackPressed;

    //inisialisasi widget pada layout
   private TextView mainNama;
   private Button btnPresent, btnlogout, btn_map;
   private SharedPrefManager session;
   private RecyclerView recyclerView;
   private ArrayList<PatrolModel> datapatrol;
   private PatrolAdapter adapterpatrol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestPermission();

        mainNama = findViewById(R.id.txt_nama);
        btnPresent = findViewById(R.id.btn_presensi);
        btnlogout = findViewById(R.id.btn_logout);
        btn_map = findViewById(R.id.btn_map);

        session = new SharedPrefManager(this);

        //menampilkan nama
        nama = session.getSpFullname();
        mainNama.setText(nama);
        presensi();
        getMaps();

        //menampilkan data
        view();

        //Logout session
        showLogout();
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION},1);
    }

    private void presensi(){
        btnPresent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //berpindah ke activity selanjutnya
                startActivity(new Intent(MainActivity.this, PresensiActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });
    }

    private void updateState(){

    }

    private void getMaps(){
        btn_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MapsActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });
    }

    public void setTime() {

        Calendar calendar = Calendar.getInstance();

        //menampilkan waktu
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        time = format.format(calendar.getTime());

        TextView txtTime = findViewById(R.id.presenJam);

        //menampilkan tanggal
        SimpleDateFormat format2 = new SimpleDateFormat("dd/MM/yyyy");
        currentDate = format2.format(calendar.getTime());

        TextView txtDate = findViewById(R.id.presenTgl);
    }

    private void view(){
        recyclerView = (RecyclerView)findViewById(R.id.listpatrol);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManagerlog = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManagerlog);
        loadJSON();

    }

    private void loadJSON(){
        ApiServices api = InitRetrofit.getInstance();
        Call<JSONResponePatrol> call = api.getPatrol(session.getSpIduser());
        call.enqueue(new Callback<JSONResponePatrol>() {
            @Override
            public void onResponse(Call<JSONResponePatrol> call, Response<JSONResponePatrol> response) {
                JSONResponePatrol jsonResponePatrol = response.body();
                datapatrol = new ArrayList<>(Arrays.asList(jsonResponePatrol.getPatrol()));
                ArrayList<LogModel> dataLOG = new ArrayList<>(Arrays.asList(jsonResponePatrol.getLog()));
                adapterpatrol = new PatrolAdapter(datapatrol, dataLOG, MainActivity.this);
                recyclerView.setAdapter(adapterpatrol);
            }

            @Override
            public void onFailure(Call<JSONResponePatrol> call, Throwable t) {
                Log.d("Error",t.getMessage());
            }
        });
    }

    private void showLogout() {
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setMessage("Apa Anda ingin keluar?").setPositiveButton("Iya", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which) {
                        logout(); // Last step. Logout function
                    }
                }).setNegativeButton("Tidak", null);

                AlertDialog alert1 = alert.create();
                alert1.show();
            }
        });
    }

    private void logout(){
        session.saveSPBoolean(session.SP_ALREADY_LOGIN, false);
        startActivity(new Intent(MainActivity.this, LoginActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
        finish();
    }

    @Override
    public void onBackPressed() {
        //Klik 2x untuk keluar
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis())
        {
            super.onBackPressed();
            return;
        }
        else {
            Toast.makeText(getBaseContext(), "Tekan Back Sekali lagi untuk Keluar", Toast.LENGTH_SHORT).show();
        }
        mBackPressed = System.currentTimeMillis();
    }
}

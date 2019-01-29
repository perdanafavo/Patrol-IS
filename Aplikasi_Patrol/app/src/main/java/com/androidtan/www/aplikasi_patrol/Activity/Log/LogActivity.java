package com.androidtan.www.aplikasi_patrol.Activity.Log;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.androidtan.www.aplikasi_patrol.Adapter.LogAdapter;
import com.androidtan.www.aplikasi_patrol.Models.LogModel;
import com.androidtan.www.aplikasi_patrol.Network.ApiServices;
import com.androidtan.www.aplikasi_patrol.Network.InitRetrofit;
import com.androidtan.www.aplikasi_patrol.R;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<LogModel> datalog;
    private LogAdapter adapterlog;
    private TextView log;
    public int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        log = findViewById(R.id.lokasiLog);
        view();
        loadData();
        loadJSON();
    }

    private void loadData(){
        Intent get = getIntent();
        this.id = get.getIntExtra("id", 0);
        log.setText(get.getStringExtra("ruas"));
    }

    private void view(){
        recyclerView = (RecyclerView)findViewById(R.id.listlog);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManagerlog = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManagerlog);
    }

    private void loadJSON(){
        ApiServices api = InitRetrofit.getInstance();
        Call<JSONResponeLog> call = api.getLog(id);
        call.enqueue(new Callback<JSONResponeLog>() {
            @Override
            public void onResponse(Call<JSONResponeLog> call, Response<JSONResponeLog> response) {
                JSONResponeLog jsonResponeLog = response.body();
                datalog = new ArrayList<>(Arrays.asList(jsonResponeLog.getActivity()));
                adapterlog = new LogAdapter(datalog);
                recyclerView.setAdapter(adapterlog);
            }

            @Override
            public void onFailure(Call<JSONResponeLog> call, Throwable t) {
                Log.d("Error",t.getMessage());
            }
        });
    }
}

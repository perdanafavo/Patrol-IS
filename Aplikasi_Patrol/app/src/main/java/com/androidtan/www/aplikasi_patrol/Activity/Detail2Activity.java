package com.androidtan.www.aplikasi_patrol.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidtan.www.aplikasi_patrol.Activity.Log.LogActivity;
import com.androidtan.www.aplikasi_patrol.Activity.Main.MainActivity;
import com.androidtan.www.aplikasi_patrol.Models.LogModel;
import com.androidtan.www.aplikasi_patrol.Models.PatrolModel;
import com.androidtan.www.aplikasi_patrol.Network.ApiServices;
import com.androidtan.www.aplikasi_patrol.Network.InitRetrofit;
import com.androidtan.www.aplikasi_patrol.R;
import com.androidtan.www.aplikasi_patrol.TestActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Detail2Activity extends AppCompatActivity {


    private RelativeLayout layoutbawah, layoutt1, layoutt2, layoutt3, layoutt4, layoutt5, layoutt6, layoutlog, garisdetail;
    private TextView tx_ruas, tx_waktu, tx_activity, tx_object, tx_nodea, tx_nodeb, tx_link, tx_status;
    private ImageView ig_state;
    private int id;
    private String time, currentDate;
    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail2);
        layoutbawah = findViewById(R.id.layoutbawah);
        layoutt1 = findViewById(R.id.layoutt1);
        layoutt2 = findViewById(R.id.layoutt2);
        layoutt3 = findViewById(R.id.layoutt3);
        layoutt4 = findViewById(R.id.layoutt4);
        layoutt5 = findViewById(R.id.layoutt5);
        layoutt6 = findViewById(R.id.layoutt6);
        tx_ruas = findViewById(R.id.tx_ruas);
        tx_waktu = findViewById(R.id.tx_waktu);
        tx_activity = findViewById(R.id.tx_activity);
        tx_object = findViewById(R.id.tx_object);
        tx_nodea = findViewById(R.id.tx_nodea);
        tx_nodeb = findViewById(R.id.tx_nodeb);
        tx_link = findViewById(R.id.tx_link);
        tx_status = findViewById(R.id.tx_status);
        ig_state = findViewById(R.id.ig_state);
        layoutlog = findViewById(R.id.layoutlog);
        garisdetail = findViewById(R.id.garisdetail);

        mDialog = new ProgressDialog(Detail2Activity.this);
        mDialog.setMessage("Please Waiting...");
        mDialog.setCancelable(false);
        mDialog.setIndeterminate(true);

        loadData();
        loadDetail();
  //      btnUpload.setVisibility(View.GONE);
    }

    private void loadData(){
        Intent get = getIntent();
        this.id = get.getIntExtra("id", 0);
    }

    public void uploadKlik(View view){
        Intent move2 = new Intent(Detail2Activity.this, TestActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        move2.putExtra("id",id);
        startActivity(move2);
    }

    public void logAct(View view){
        Intent move = new Intent(Detail2Activity.this, LogActivity.class);
        move.putExtra("id",id);
        move.putExtra("ruas", tx_ruas.getText());
        startActivity(move);
    }

    public void cekState(){
        layoutlog.setVisibility(View.VISIBLE);
        layoutbawah.setVisibility(View.GONE);
        layoutt1.setClickable(false);
        layoutt2.setClickable(false);
        layoutt3.setClickable(false);
        layoutt4.setClickable(false);
        layoutt5.setClickable(false);
        layoutt6.setClickable(false);
    }

    public void setAct(View v){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Masukkan Activity");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        alert.setView(input);
        int bla = tx_activity.getText().length();
        input.append(tx_activity.getText(), 0, bla);
        alert.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        tx_activity.setText(input.getText().toString().trim());
                        }
                });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // do nothing
            }
        });
        alert.show();
    }

    public void selectObject(View v){
        final String[] items = new String[8];

        items[0] = "Kabel";
        items[1] = "Slack";
        items[2] = "Sambungan";
        items[3] = "Pole";
        items[4] = "Hole";
        items[5] = "Duct";
        items[6] = "Lingkungan";
        items[7] = "Lain-Lain";

        // Create List Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pilih Object");
        builder.setItems(items, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int item) {
                tx_object.setText(items[item]);
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void selectNodeA(View v){
        final String[] items = new String[6];

        items[0] = "Node A";
        items[1] = "Node B";
        items[2] = "Node C";
        items[3] = "Node D";
        items[4] = "Node E";
        items[5] = "Node F";

        // Create List Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pilih Node A");
        builder.setItems(items, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int item) {
                tx_nodea.setText(items[item]);
                tx_link.setText("");
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void selectNodeB(View v){

        final String[] items = new String[6];

        items[0] = "Node A";
        items[1] = "Node B";
        items[2] = "Node C";
        items[3] = "Node D";
        items[4] = "Node E";
        items[5] = "Node F";

        // Create List Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pilih Node B");
        builder.setItems(items, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int item) {
                tx_nodeb.setText(items[item]);
                tx_link.setText("");
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void selectLink(View v){
        final String[] items = new String[1];

        items[0] = tx_nodea.getText()+" - "+tx_nodeb.getText();

        // Create List Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pilih Link");
        builder.setItems(items, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int item) {
                tx_link.setText(items[item]);
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void setStatus(View v){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Masukkan Status");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        alert.setView(input);
        int bla = tx_status.getText().length();
        input.append(tx_status.getText(), 0, bla);
        alert.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        tx_status.setText(input.getText().toString().trim());
                    }
                });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // do nothing
            }
        });
        alert.show();
    }

    public void btnActivity(View v){
        android.support.v7.app.AlertDialog.Builder alert = new android.support.v7.app.AlertDialog.Builder(Detail2Activity.this);
        alert.setMessage("Apa Anda ingin menambahkan Activity?").setPositiveButton("Iya", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which) {
                setTime();
                addActivity();
                updatePatrol(); // Last step. Logout function
            }
        }).setNegativeButton("Tidak", null);
        android.support.v7.app.AlertDialog alert1 = alert.create();
        alert1.show();
    }

    public void updatePatrol() {
        ApiServices api = InitRetrofit.getInstance();
        Call<PatrolModel> changeCall = api.postPatrol(id, tx_activity.getText().toString(), tx_object.getText().toString(), tx_nodea.getText().toString(), tx_nodeb.getText().toString(), tx_link.getText().toString(),tx_status.getText().toString());
        changeCall.enqueue(new Callback<PatrolModel>() {
            @Override
            public void onResponse(Call<PatrolModel> call, Response<PatrolModel> response) {
                if (response.isSuccessful()) {
                        new android.support.v7.app.AlertDialog.Builder(Detail2Activity.this)
                                .setTitle("Update Activity")
                                .setMessage("Activity berhasil ditambahkan")
                                .setPositiveButton("Oke", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        Intent main = new Intent(Detail2Activity.this, MainActivity.class);
                                        main.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(main);
                                        finish();
                                    }
                                }).show();

                } else {

                }
            }
            @Override
            public void onFailure(Call<PatrolModel> call, Throwable t) {

            }
        });

    }

    public void setTime() {

        Calendar calendar = Calendar.getInstance();

        //menampilkan waktu
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        time = format.format(calendar.getTime());

        //menampilkan tanggal
        SimpleDateFormat format2 = new SimpleDateFormat("dd/MM/yyyy");
        currentDate = format2.format(calendar.getTime());
    }

    public void addActivity() {
        ApiServices api = InitRetrofit.getInstance();
        Call<LogModel> changeCall = api.postActivity(id, tx_activity.getText().toString(), time, currentDate);
        changeCall.enqueue(new Callback<LogModel>() {
            @Override
            public void onResponse(Call<LogModel> call, Response<LogModel> response) {
                if (response.isSuccessful()) {

                } else {

                }
            }
            @Override
            public void onFailure(Call<LogModel> call, Throwable t) {

            }
        });
    }

    private void loadDetail(){
        mDialog.show();
        ApiServices api = InitRetrofit.getInstance();
        Call<PatrolModel> loadDetail = api.getDetail(id);
        loadDetail.enqueue(new Callback<PatrolModel>() {
            @Override
            public void onResponse(Call<PatrolModel> call, Response<PatrolModel> response){
                if (response.isSuccessful()) {
                    mDialog.dismiss();
                    tx_activity.setText(response.body().getACT());
                    tx_object.setText(response.body().getOBJECT());
                    tx_nodea.setText(response.body().getNODE_A());
                    tx_nodeb.setText(response.body().getNODE_B());
                    tx_link.setText(response.body().getLINK());
                    tx_status.setText(response.body().getSTATUS());
                    tx_ruas.setText(response.body().getRUAS());
                    tx_waktu.setText(response.body().getTGL());
                    String state = response.body().getSTATE();
                    if(state.equals("EXPIRED")){
                        ig_state.setImageResource(R.drawable.ic_red);
                        cekState();
                    }
                    else if (state.equals("CLOSE")){
                        ig_state.setImageResource(R.drawable.ic_green);
                        cekState();
                    }
                    else {
                        ig_state.setImageResource(R.drawable.ic_black);
                    }

                } else {
                }
            }

            @Override
            public void onFailure(Call<PatrolModel> call, Throwable t) {
                Log.e("debug", "onFailure: ERROR > " + t.toString());
                finish();
            }
    });
    }
}

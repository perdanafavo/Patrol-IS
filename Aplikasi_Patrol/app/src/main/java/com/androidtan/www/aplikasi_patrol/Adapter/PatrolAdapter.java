package com.androidtan.www.aplikasi_patrol.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidtan.www.aplikasi_patrol.Activity.Detail2Activity;
import com.androidtan.www.aplikasi_patrol.Models.LogModel;
import com.androidtan.www.aplikasi_patrol.Models.PatrolModel;
import com.androidtan.www.aplikasi_patrol.Network.ApiServices;
import com.androidtan.www.aplikasi_patrol.Network.InitRetrofit;
import com.androidtan.www.aplikasi_patrol.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PatrolAdapter extends RecyclerView.Adapter<PatrolAdapter.ViewHolder> {
    private ArrayList<LogModel> dataLOG;
    private Context mContext;
    private ArrayList<PatrolModel> patrol;

    public PatrolAdapter(ArrayList<PatrolModel> patrol, ArrayList<LogModel> dataLOG, Context mContext){
        this.mContext = mContext;
        this.patrol = patrol;
        this.dataLOG = dataLOG;
    }

    int id;
    String newState, currentDate;

    @Override
    public PatrolAdapter.ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item,parent,false);
        return new ViewHolder(v);
    }

    public void updatePatrol() {
        ApiServices api = InitRetrofit.getInstance();
        Call<PatrolModel> changeCall = api.updateState(id, newState);
        changeCall.enqueue(new Callback<PatrolModel>() {
            @Override
            public void onResponse(Call<PatrolModel> call, Response<PatrolModel> response) {
                if (response.isSuccessful()) {

                } else {

                }
            }
            @Override
            public void onFailure(Call<PatrolModel> call, Throwable t) {

            }
        });
    }

    public void cekLog() {
        ApiServices api = InitRetrofit.getInstance();
        Call<LogModel> checkLog = api.cekLog(id);
        checkLog.enqueue(new Callback<LogModel>() {
            @Override
            public void onResponse(Call<LogModel> call, Response<LogModel> response) {
                if (response.isSuccessful()) {
                    boolean status = response.body().getSuccess();
                    if (status){
                        newState = "CLOSE";
                    }
                    else {
                        newState = "EXPIRED";
                    }
                } else {

                }
            }
            @Override
            public void onFailure(Call<LogModel> call, Throwable t) {

            }
        });
    }

    public void setTime() {

        Calendar calendar = Calendar.getInstance();

        //menampilkan tanggal
        SimpleDateFormat format2 = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        currentDate = format2.format(calendar.getTime());
    }

    @Override
    public void onBindViewHolder(PatrolAdapter.ViewHolder holder, final int position) {
        holder.recycle_place.setText(patrol.get(position).getRUAS());
        holder.recycle_info.setText(patrol.get(position).getACT());
        holder.recycle_date.setText(patrol.get(position).getTGL());
        id = patrol.get(position).getID_PATROL();
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat format2 = new SimpleDateFormat("dd/MM/yyyy");
        String tggl = patrol.get(position).getTGL();
        currentDate = format2.format(calendar.getTime());
        try {
            Date patrolDate = format2.parse(tggl);
            Date tglSkarang = format2.parse(currentDate);
            if (tglSkarang.after(patrolDate)){
                newState="EXPIRED";
                for (int i =0; i<dataLOG.size(); i++){
                   if (Integer.parseInt(dataLOG.get(i).getID_PATROL())== id){
                       newState="CLOSE";
                       break;
                   }
                }
            }
            else {
                newState = "OPEN";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        updatePatrol();
        patrol.get(position).setSTATE(newState);

        if (patrol.get(position).getSTATE().equals("EXPIRED")){
            holder.image_state.setImageResource(R.drawable.ic_red);
        }
        else if (patrol.get(position).getSTATE().equals("CLOSE")){
            holder.image_state.setImageResource(R.drawable.ic_green);
        }
        else {
            holder.image_state.setImageResource(R.drawable.ic_black);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                    Intent move = new Intent(mContext, Detail2Activity.class);
                    move.putExtra("id", patrol.get(position).getID_PATROL());
                    mContext.startActivities(new Intent[]{move});
            }
        });

    }

    @Override
    public int getItemCount() {
        return patrol.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView recycle_date, recycle_info, recycle_place;
        private ImageView image_state;

        public ViewHolder(View view){
            super(view);

            recycle_date = (TextView)view.findViewById(R.id.recycle_date);
            recycle_info = (TextView)view.findViewById(R.id.recycle_info);
            recycle_place = (TextView)view.findViewById(R.id.recycle_place);
            image_state = (ImageView)view.findViewById(R.id.image_state);
        }
    }

}
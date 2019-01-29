package com.androidtan.www.aplikasi_patrol.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androidtan.www.aplikasi_patrol.Models.LogModel;
import com.androidtan.www.aplikasi_patrol.R;

import java.util.ArrayList;

public class LogAdapter extends RecyclerView.Adapter<LogAdapter.ViewHolder> {
    private ArrayList<LogModel> activity;
    public LogAdapter(ArrayList<LogModel> activity){
        this.activity = activity;
    }

    @Override
    public LogAdapter.ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item2,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder( LogAdapter.ViewHolder holder, int position) {
        holder.recycle_tanggallog.setText(activity.get(position).getTANGGAL());
        holder.recycle_waktulog.setText(activity.get(position).getWAKTU());
        holder.recycle_keteranganlog.setText(activity.get(position).getKETERANGAN());


    }

    @Override
    public int getItemCount() {
        return activity.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView recycle_tanggallog, recycle_waktulog, recycle_keteranganlog;
        public ViewHolder(View view){
            super(view);

            recycle_tanggallog = (TextView)view.findViewById(R.id.recycle_tanggallog);
            recycle_waktulog = (TextView)view.findViewById(R.id.recycle_waktulog);
            recycle_keteranganlog = (TextView)view.findViewById(R.id.recycle_keteranganlog);
        }
    }

}

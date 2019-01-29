package com.androidtan.www.aplikasi_patrol.Network;

import com.androidtan.www.aplikasi_patrol.Activity.Main.JSONResponePatrol;
import com.androidtan.www.aplikasi_patrol.Models.LogModel;
import com.androidtan.www.aplikasi_patrol.Models.PatrolModel;
import com.androidtan.www.aplikasi_patrol.Models.PresensiModel;
import com.androidtan.www.aplikasi_patrol.Activity.Log.JSONResponeLog;
import com.androidtan.www.aplikasi_patrol.Models.UserModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiServices {

    @FormUrlEncoded
    @POST("getUser.php")
    Call<UserModel> requestLogin(@Field("username") String Username,
                                 @Field("password") String Password);
    @GET("getAllLog.php")
    Call<JSONResponeLog> getLog(@Query("idpatrol") int idpatrol);

    @GET("getAllPatrol.php")
    Call<JSONResponePatrol> getPatrol(@Query("iduser") int iduser);

    @FormUrlEncoded
    @POST("postAbsensi.php")
    Call<PresensiModel> setAbsensi(@Field("id") int id,
                                   @Field("tanggal") String tanggal,
                                   @Field("waktu") String waktu);

    @GET("getAbsensi.php")
    Call<PresensiModel> getAbsensi(@Query("id") int id);

    @FormUrlEncoded
    @POST("getDetail.php")
    Call<PatrolModel> getDetail(@Field("idpatrol") int idpatrol);

    @FormUrlEncoded
    @POST("postPatrol.php")
    Call<PatrolModel> postPatrol(@Field("id") int id,
                                 @Field("activity") String activity,
                                 @Field("object") String object,
                                 @Field("nodeA") String nodeA,
                                 @Field("nodeB") String nodeB,
                                 @Field("link") String link,
                                 @Field("status") String status);

    @FormUrlEncoded
    @POST("updatePatrol.php")
    Call<PatrolModel> updatePatrol(@Field("id_patrol") int id_patrol,
                                   @Field("Gambar") String Gambar,
                                   @Field("latitude") String latitude,
                                   @Field("longitude") String longitude);

    @FormUrlEncoded
    @POST("postActivity.php")
    Call<LogModel> postActivity(@Field("id") int id,
                                @Field("keterangan") String keterangan,
                                @Field("waktu") String waktu,
                                @Field("tanggal") String tanggal);

    @FormUrlEncoded
    @POST("updateState.php")
    Call<PatrolModel> updateState(@Field("id") int id,
                                 @Field("state") String state);


    @FormUrlEncoded
    @POST("cekLog.php")
    Call<LogModel> cekLog(@Field("id") int id);

}

package com.androidtan.www.aplikasi_patrol.Network;

import com.androidtan.www.aplikasi_patrol.Config.MyConstant;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InitRetrofit {

    public static Retrofit setInit(){
        return new Retrofit.Builder()
                .baseUrl(MyConstant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static ApiServices getInstance(){
        return setInit().create(ApiServices.class);
    }
}

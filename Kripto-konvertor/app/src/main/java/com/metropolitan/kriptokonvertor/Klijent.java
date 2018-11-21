package com.metropolitan.kriptokonvertor;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Klijent {
    private static final String glavni_url = "https://min-api.cryptocompare.com/";
    Retrofit retrofit = null;
    public Retrofit getKlijent(){
        if (retrofit==null){
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient klijent = new OkHttpClient.Builder().addInterceptor(interceptor).build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(glavni_url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(klijent)
                    .build();
        }
        return retrofit;
    }
}

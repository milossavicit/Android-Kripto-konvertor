package com.metropolitan.kriptokonvertor;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Servis {
    @GET("data/pricemulti?fsyms=ETH,BTC&tsyms=RSD,BAM,NGN,USD,GBP,GHS,EUR,XAF,CAD,AFN,ALL,COP,EGP,DKK,KRW,ZAR,SAR,JPY,ARS,BRL,CNY,HKD")
    Call<JsonObject> getKurs();
}

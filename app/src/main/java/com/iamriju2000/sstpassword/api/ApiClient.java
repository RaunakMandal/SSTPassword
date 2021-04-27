package com.iamriju2000.sstpassword.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.iamriju2000.sstpassword.constants.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ApiClient {
    public static ApiInterface fetchData() {
        Gson gson = new GsonBuilder()
                .setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        return apiInterface;
    }
}

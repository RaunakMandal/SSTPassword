package com.iamriju2000.sstpassword.api;

import com.iamriju2000.sstpassword.data.Finance;
import com.iamriju2000.sstpassword.data.Personal;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("personal")
    Call<ArrayList<Personal>> getPersonal(@Query("key") String key);
    @GET("finance")
    Call<ArrayList<Finance>> getFinance(@Query("key") String key);

    @POST("{type}/add")
    Call<String> addNewData(@Path("type") String type, @Query("key") String key, @Body Map<String, String> personal);
    @PUT("{type}/edit/{id}")
    Call<String> editById(@Path("type") String type, @Path("id") String id, @Query("key") String key, @Body Map<String, String> personal);
    @DELETE("{type}/delete/{id}")
    Call<String> deleteById(@Path("type") String type, @Path("id") String id, @Query("key") String key);
}

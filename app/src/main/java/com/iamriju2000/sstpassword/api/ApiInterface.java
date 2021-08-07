package com.iamriju2000.sstpassword.api;

import com.iamriju2000.sstpassword.data.Finance;
import com.iamriju2000.sstpassword.data.Personal;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("personal")
    Call<List<Personal>> getPersonal(@Header("Authorization") String authKey, @Query("key") String key);
    @POST("personal/add")
    Call<Personal> addPersonal(@Header("Authorization") String authKey, @Query("key") String key, @Body Map<String, String> personal);
    @PUT("personal/edit/{id}")
    Call<Personal> editPersonal(@Path("id") String id, @Header("Authorization") String authKey, @Query("key") String key, @Body Map<String, String> personal);
    @DELETE("personal/delete/{id}")
    Call<String> deletePersonal(@Path("id") String id, @Header("Authorization") String authKey, @Query("key") String key);


    @GET("finance")
    Call<List<Finance>> getFinance(@Header("Authorization") String authKey, @Query("key") String key);
}

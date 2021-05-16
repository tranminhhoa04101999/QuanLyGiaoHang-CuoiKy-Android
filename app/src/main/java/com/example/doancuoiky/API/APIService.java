package com.example.doancuoiky.API;

import com.example.doancuoiky.model.Role;
import com.example.doancuoiky.model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIService {

    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd")
            .create();

    APIService apiService = new Retrofit.Builder()
            .baseUrl("http://192.168.1.8:8080/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(APIService.class);

    @GET("login")
    Call<ArrayList<User>> getUser(@Query("username") String username,
                                 @Query("password") String password
                        );
    @GET("roles")
    Call<ArrayList<Role>>  getRole();
}

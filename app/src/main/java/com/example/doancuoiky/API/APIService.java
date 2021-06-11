package com.example.doancuoiky.API;

import com.example.doancuoiky.model.Client;
import com.example.doancuoiky.model.Role;
import com.example.doancuoiky.model.Task;
import com.example.doancuoiky.model.TaskDetail;
import com.example.doancuoiky.model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIService {

    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd")
            .create();

    APIService apiService = new Retrofit.Builder()
            .baseUrl("http://192.168.1.6:8080/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(APIService.class);

    @GET("login")
    Call<ArrayList<User>> getUser(@Query("username") String username,
                                  @Query("password") String password
    );

    @GET("roles")
    Call<ArrayList<Role>> getRoles();

    @GET("tasks")
    Call<ArrayList<Task>> getTasks();

    @GET("clients")
    Call<ArrayList<Client>> getClients();

    @GET("users")
    Call<ArrayList<User>> getUsers();

    @GET("/users/role/1")
    Call<ArrayList<User>> getDSTaiXe();

    //Task
    @GET("task/noApprove")
    Call<ArrayList<Task>> getDSTaskChuaApprove();

    @POST("AddTaskClient/{idClient}")
    Call<Void> addTask(@Body Task task, @Path("idClient") int idClient);

    @DELETE("tasks/{id}")
    Call<Void> deleteTask(@Path("id") int id);

    @POST("UpdateTaskClient/{idClient}")
    Call<Void> UpdateTask(@Body Task task, @Path("idClient") int idClient);

    // User
    @GET("user/username/{username}")
    Call<ArrayList<User>> getUserKH(@Path("username") String username);

    @POST("AddUser/{idRole}")
    Call<Void> addUser(@Body User user, @Path("idRole") int idRole);

    @DELETE("users/{id}")
    Call<Void> deleteUser(@Path("id") int id);

    @POST("user/delete/{username}")
    Call<Void> deleteUserUsername(@Path("username") String username);

    //Client
    @DELETE("clients/{id}")
    Call<Void> deleteClient(@Path("id") int id);

    @POST("client/delete/{username}")
    Call<Void> deleteClientUsername(@Path("username") String username);

    @POST("clients")
    Call<Void> addClient(@Body Client client);

    //Task details
    @GET("taskdetails")
    Call<ArrayList<TaskDetail>> getTaskDetails();

    @POST("addTaskdetails/{userid}")
    Call<Void> addTaskDetail(@Body TaskDetail taskDetail,@Path("userid")int id);

    @DELETE("taskdetails/{id}")
    Call<Void> deleteTaskDetail(@Path("id") int id);


}

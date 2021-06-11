package com.example.doancuoiky.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doancuoiky.API.APIService;
import com.example.doancuoiky.R;
import com.example.doancuoiky.model.TaskDetail;
import com.example.doancuoiky.model.User;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.microsoft.maps.Geopoint;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManHinhChat extends AppCompatActivity {
    TextView tvChat;
    EditText etChat;
    TaskDetail taskDetail;
    int taskid;
    int taskDetailId;

    //phan nay la cua map

    FusedLocationProviderClient fusedLocationProviderClient;
    ;
    List<Address> addresses;



    Handler handler = new Handler();
    Runnable runnable;
    int delay = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_chat);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(ManHinhChat.this);

        taskid = getIntent().getIntExtra("taskid", 0);
        Log.e("a", taskid + " " + taskDetailId);

        ScrollView scrollView = findViewById(R.id.myscrollview);
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
        tvChat = findViewById(R.id.tvChat);

        EditText etChat = findViewById(R.id.etChat);
        etChat.setText("");
        Button btnSend = findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



//                Log.e("senddd", taskDetailId + "");
                String uname = getIntent().getStringExtra("role").equals("kh") ? taskDetail.getTask().getClient().getUsername() : taskDetail.getUser().getName();
                String newchat = tvChat.getText().toString() + "\n" + uname + ": " + etChat.getText().toString();
//                Log.e("aaa", newchat);
//                newchat.replace("\r", "");
                tvChat.setText(newchat);//+ System.getProperty("line.separator") +
                taskDetail.setChat(newchat);
                APIService.apiService.updateTaskDetailbyTaskDetailId(taskDetail, taskDetailId).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {

                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });
//                APIService.apiService.updateChat(newchat, taskDetailId).enqueue(new Callback<Void>() {
//                    @Override
//                    public void onResponse(Call<Void> call, Response<Void> response) {
//                        Log.e("test",newchat);
//                    }
//
//                    @Override
//                    public void onFailure(Call<Void> call, Throwable t) {
//                        Log.e("senddd", t.getMessage());
//
//                    }
//                });
                etChat.setText("");
                scrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                    }
                });
            }
        });
    }

    @Override
    protected void onResume() {
        handler.postDelayed(runnable = new Runnable() {
            public void run() {
                handler.postDelayed(runnable, delay);
//                Toast.makeText(ManHinhChat.this, etChat.getText().toString(), Toast.LENGTH_SHORT).show();
                APIService.apiService.getTaskDetailByTaskId(taskid).enqueue(new Callback<ArrayList<TaskDetail>>() {
                    @Override
                    public void onResponse(Call<ArrayList<TaskDetail>> call, Response<ArrayList<TaskDetail>> response) {
                        tvChat.setText(response.body().get(0).getChat());
                        taskDetailId = response.body().get(0).getId();
                        taskDetail = response.body().get(0);
                    }

                    @Override
                    public void onFailure(Call<ArrayList<TaskDetail>> call, Throwable t) {

                    }
                });
                
                if (getIntent().getStringExtra("role").equals("tx")){
                    if (ActivityCompat.checkSelfPermission(ManHinhChat.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(Task<Location> task) {
                                Location location = task.getResult();
                                if (location != null) {
                                    try {
                                        Geocoder geocoder = new Geocoder(ManHinhChat.this, Locale.getDefault());
                                        addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
//                                        Log.e("ec", addresses.get(0).getLatitude() + " " + addresses.get(0).getLongitude());
                                        User user = taskDetail.getUser();
                                        user.setLat(addresses.get(0).getLatitude());
                                        user.setLng(addresses.get(0).getLongitude());
                                        APIService.apiService.updateUserById(user,taskDetail.getUser().getId()).enqueue(new Callback<Void>() {
                                            @Override
                                            public void onResponse(Call<Void> call, Response<Void> response) {

                                            }

                                            @Override
                                            public void onFailure(Call<Void> call, Throwable t) {
                                                Log.e("add location fal","");

                                            }
                                        });

                                    } catch (IOException e) {
                                        Toast.makeText(ManHinhChat.this, "that bai catch", Toast.LENGTH_SHORT).show();
                                    }

                                } else {
                                    Toast.makeText(ManHinhChat.this, "that bai", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


                    } else {
                        ActivityCompat.requestPermissions(ManHinhChat.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                    }
                }

            }
        }, delay);
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable); //stop handler when activity not visible super.onPause();
    }
}

//class TheadSimple extends Thread {
//    public static String chat = "";
//
//    public void run() {
//        int i = 0;
//        while (true) {
//
//            chat += i++ + "\n";
//            Log.e("aaa", chat);
//            try {
//                sleep(5000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    public static void main() {
//        TheadSimple t1 = new TheadSimple();
//        t1.start();
//    }
//}
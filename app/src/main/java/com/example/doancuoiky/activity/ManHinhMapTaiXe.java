package com.example.doancuoiky.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.doancuoiky.API.APIService;
import com.example.doancuoiky.model.User;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.microsoft.maps.Geopoint;
import com.example.doancuoiky.R;
import com.microsoft.maps.MapAnimationKind;
import com.microsoft.maps.MapElementLayer;
import com.microsoft.maps.MapIcon;
import com.microsoft.maps.MapRenderMode;
import com.microsoft.maps.MapScene;
import com.microsoft.maps.MapView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManHinhMapTaiXe extends AppCompatActivity {
    Button btn;
    private MapView mMapView;
    private MapElementLayer mPinLayer;


    //phan nay la cua map
    private static final Geopoint LAKE_WASHINGTON = new Geopoint(10.8486, 106.7897);
    FusedLocationProviderClient fusedLocationProviderClient;
    ;
    List<Address> addresses;

    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_map_tai_xe);
        btn = findViewById(R.id.ecec);


        mMapView = new MapView(this, MapRenderMode.VECTOR);  // or use MapRenderMode.RASTER for 2D map
        mMapView.setCredentialsKey("Ai7DgnDiSxfayqjt2UQdkBfckPq4_3-xRtbmcaSl95Cu0SJEHEHyDRcz1WwP3Md7");
        ((FrameLayout) findViewById(R.id.map_view)).addView(mMapView);
        mMapView.onCreate(savedInstanceState);
        mPinLayer = new MapElementLayer();


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                APIService.apiService.layUserByTaskId(getIntent().getIntExtra("taskid",0)).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        mPinLayer.getElements().clear();
                        User user = response.body();
                        Geopoint piont = new Geopoint(user.getLat(), user.getLng());
                        mMapView.getLayers().add(mPinLayer);
                        MapIcon pushpin = new MapIcon();
                        pushpin.setLocation(piont);
                        mPinLayer.getElements().add(pushpin);

                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {

                    }
                });

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mMapView.setScene(
                MapScene.createFromLocationAndZoomLevel(LAKE_WASHINGTON, 10),
                MapAnimationKind.NONE);

    }
}
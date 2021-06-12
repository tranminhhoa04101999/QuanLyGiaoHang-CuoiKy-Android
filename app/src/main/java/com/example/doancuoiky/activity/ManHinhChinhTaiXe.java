package com.example.doancuoiky.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doancuoiky.R;
import com.example.doancuoiky.model.User;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class ManHinhChinhTaiXe extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;

    private int id;
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_chinh_tai_xe);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView= findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this , drawer, toolbar ,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        //lấy thông tin user từ bundle
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();

        User thumbs= (User)bundle.getSerializable("objectUser");
        setId(thumbs.getId());
        setUser(thumbs);
        /*Log.e("helo","" +thumbs.getId());
        Log.e("helo","" +thumbs.getName());
        Log.e("helo","" +thumbs.getPassword());
        Log.e("helo","" +thumbs.getId());*/
        //NavigationView nav_view = (NavigationView) findViewById(R.id.nav_view);
        View hView = navigationView.getHeaderView(0);
        TextView tvTenTX = (TextView) hView.findViewById(R.id.HeaderUserName);
        tvTenTX.setText(thumbs.getName());

        TextView address = (TextView) hView.findViewById(R.id.HeaderDiaChi);
        address.setText(thumbs.getAddress());

        if(savedInstanceState==null) {

            TinNhanTaiXeFragment tinNhanTaiXeFragment = new TinNhanTaiXeFragment();
            Bundle bundle2 = new Bundle();

            bundle2.putInt("id", getId());
            tinNhanTaiXeFragment.setArguments(bundle2);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,tinNhanTaiXeFragment).commit();
        }
        toggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.taixe_misson:
                NhiemVuTaiXeFragment nhiemVuTaiXeFragment = new NhiemVuTaiXeFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("id", getId());
                nhiemVuTaiXeFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,nhiemVuTaiXeFragment).commit();
                break;
            case R.id.nav_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new TrangCaNhanTaiXeFragment()).commit();
                break;
            case R.id.Contact:
                Toast.makeText(this,"021312398413",Toast.LENGTH_LONG).show();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }
}
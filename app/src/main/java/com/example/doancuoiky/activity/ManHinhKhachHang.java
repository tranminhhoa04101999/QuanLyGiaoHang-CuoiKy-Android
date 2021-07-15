package com.example.doancuoiky.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.doancuoiky.R;
import com.example.doancuoiky.model.User;
import com.google.android.material.navigation.NavigationView;

public class ManHinhKhachHang extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;

    private String usermame;
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUsermame() {
        return usermame;
    }

    public void setUsermame(String usermame) {
        this.usermame = usermame;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_khach);

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

        User kh= (User)bundle.getSerializable("objectUser");
        setUsermame(kh.getUsername());

        View hView = navigationView.getHeaderView(0);
        TextView tvTenTX = (TextView) hView.findViewById(R.id.HeaderUserName);
        tvTenTX.setText(kh.getName());

        TextView address = (TextView) hView.findViewById(R.id.HeaderDiaChi);
        address.setText(kh.getAddress());
       // setUser(kh);
        if(savedInstanceState==null) {

            XemTaskKhachHangFragment xemTaskKhachHangFragment = new XemTaskKhachHangFragment();
            Bundle bundle2 = new Bundle();
            Log.e("heloo", getUsermame());

            bundle2.putString("id", getUsermame());
            xemTaskKhachHangFragment.setArguments(bundle2);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,xemTaskKhachHangFragment).commit();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            //case R.id.nav_message:
             //   getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new T()).commit();
             //   break;
            case R.id.taixe_misson:
                XemTaskKhachHangFragment xemTaskKhachHangFragment = new XemTaskKhachHangFragment();
                Bundle bundle = new Bundle();
                bundle.putString("id", getUsermame());
                xemTaskKhachHangFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,xemTaskKhachHangFragment).commit();
                break;
            case R.id.nav_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new TrangCaNhanKhachHangFragment()).commit();
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
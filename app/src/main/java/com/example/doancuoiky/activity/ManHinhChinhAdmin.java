package com.example.doancuoiky.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.doancuoiky.R;

public class ManHinhChinhAdmin extends AppCompatActivity {

    Toolbar toolbar ;
    LinearLayout layoutTask, layoutKH, layoutUser,layoutTK;
    TextView tvInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_chinh_admin);



        toolbar=findViewById(R.id.toolbar_manhinhchinhadmin);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Quản lý giao hàng");

        setControl();

        Intent intentTask = new Intent(this, ManHinhChinhNhiemVu.class);
        layoutTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intentTask);
            }
        });

        Intent intentUser = new Intent(this,ManHinhChinhTaiKhoan.class);
        layoutUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intentUser);
            }
        });

        Intent intentKH = new Intent(this,ManHinhChinhKhachHang.class);
        layoutKH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intentKH);
            }
        });
        Intent intentTk = new Intent(this, ThongKe.class);
        layoutTK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intentTk);
            }
        });

    }
    private void setControl() {
        layoutTask = findViewById(R.id.layoutsanpham);
        layoutKH = findViewById(R.id.layoutKH);
        layoutUser = findViewById(R.id.layoutDDH);
        layoutTK = findViewById(R.id.layoutTK);
//        tvInfo=findViewById(R.id.tvInfo);
    }
}
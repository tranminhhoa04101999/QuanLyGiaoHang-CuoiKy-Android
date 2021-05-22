package com.example.doancuoiky.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.doancuoiky.R;

public class ManHinhChinhAdmin extends AppCompatActivity {

    Toolbar toolbar ;
    LinearLayout layoutSP, layoutKH,layoutDDH,layoutTK;
    TextView tvInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_chinh_admin);



        toolbar=findViewById(R.id.toolbar_manhinhchinhadmin);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Quyền Admin");

        setControl();

        Intent intentTask = new Intent(this, ManHinhNhiemVu.class);
        layoutSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intentTask);
            }
        });

        Intent intentKH = new Intent(this,ManHinhChinhKhachHang.class);
        layoutKH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intentKH);
            }
        });
//
//        Intent intentDDH = new Intent(this,QLDonDatHang.class);
//        layoutDDH.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(intentDDH);
//            }
//        });
//
//        Intent intentTK = new Intent(this,HienThiTopSanPham.class);
//        layoutTK.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(intentTK);
//            }
//        });
//
//        tvInfo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Dialog infoDialog = new Dialog(TrangChu.this);
//                infoDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                infoDialog.setContentView(R.layout.dialog_thong_tin);
//                infoDialog.show();
//            }
//        });

    }
    private void setControl() {
        layoutSP = findViewById(R.id.layoutsanpham);
        layoutKH = findViewById(R.id.layoutKH);
        layoutDDH = findViewById(R.id.layoutDDH);
        layoutTK = findViewById(R.id.layoutTK);
        tvInfo=findViewById(R.id.tvInfo);
    }
}
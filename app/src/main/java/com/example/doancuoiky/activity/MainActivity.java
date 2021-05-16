package com.example.doancuoiky.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.doancuoiky.R;

public class MainActivity extends AppCompatActivity {

    private static int SPFLASH_SCREEN = 3000;

    Animation topAim,bottomAim;
    ImageView img;
    TextView logo,slogan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        //Animation
        topAim = AnimationUtils.loadAnimation(this,R.anim.top_main);
        bottomAim = AnimationUtils.loadAnimation(this,R.anim.bottom_main);

        setControl();
        img.setAnimation(topAim);
        logo.setAnimation(bottomAim);
        slogan.setAnimation(bottomAim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this,Login.class);
                startActivity(intent);
                finish();
            }
        },SPFLASH_SCREEN);
    }

    private void setControl() {
        img = findViewById(R.id.img_main);
        logo = findViewById(R.id.tv_logo);
        slogan = findViewById(R.id.tv_slogan);
    }
}
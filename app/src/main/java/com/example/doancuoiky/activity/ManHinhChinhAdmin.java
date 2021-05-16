package com.example.doancuoiky.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.doancuoiky.Fragment.CaNhanFragment_admin;
import com.example.doancuoiky.Fragment.HomeFragment_admin;
import com.example.doancuoiky.Fragment.NhiemVuFragment_admin;
import com.example.doancuoiky.Fragment.ThongKeFragment_admin;
import com.example.doancuoiky.R;

public class ManHinhChinhAdmin extends AppCompatActivity {

    MeowBottomNavigation bottomNavigation;
    private static final  int ID_HOME = 1 ;
    private static final  int ID_THONGKE = 2 ;
    private static final  int ID_NHIEMVU = 3 ;
    private static final  int ID_CANHAN = 4 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_chinh_admin);

        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.add(new MeowBottomNavigation.Model(ID_HOME,R.drawable.ic_home));
        bottomNavigation.add(new MeowBottomNavigation.Model(ID_THONGKE,R.drawable.ic_chart));
        bottomNavigation.add(new MeowBottomNavigation.Model(ID_NHIEMVU,R.drawable.ic_mission));
        bottomNavigation.add(new MeowBottomNavigation.Model(ID_CANHAN,R.drawable.ic_person));


        bottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {

            }
        });

        bottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {

            }
        });

        bottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                Fragment fragment = null;
                switch (item.getId())
                {
                    case ID_HOME:
                        fragment = new HomeFragment_admin();
                        break;
                    case ID_NHIEMVU:
                        fragment = new NhiemVuFragment_admin();
                        break;
                    case ID_THONGKE:
                        fragment = new ThongKeFragment_admin();
                        break;
                    case ID_CANHAN:
                        fragment = new CaNhanFragment_admin();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.root_contrainer,fragment).commit();
            }
        });
    }
}
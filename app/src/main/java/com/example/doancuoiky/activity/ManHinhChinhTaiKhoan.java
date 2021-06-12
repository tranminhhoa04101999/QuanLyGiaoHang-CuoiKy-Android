package com.example.doancuoiky.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.doancuoiky.API.APIService;
import com.example.doancuoiky.R;
import com.example.doancuoiky.adapter.RoleSpinnerAdapter;
import com.example.doancuoiky.adapter.UserAdapter;
import com.example.doancuoiky.model.Role;
import com.example.doancuoiky.model.Task;
import com.example.doancuoiky.model.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManHinhChinhTaiKhoan extends AppCompatActivity {
    ListView lvTaiKhoan;
    ArrayList<User> userArrayList;
    UserAdapter userAdapter;

    Handler handler = new Handler();
    Runnable runnable;
    int delay = 500;


    @Override
    protected void onResume() {
        handler.postDelayed( runnable = new Runnable() {
            public void run() {
                //do something
                getDataUser();
                handler.postDelayed(runnable, delay);
            }
        }, delay);
        super.onResume();
    }


    @Override
    protected void onPause() {
        handler.removeCallbacks(runnable);
        super.onPause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_chinh_tai_khoan);
        lvTaiKhoan = findViewById(R.id.lvTask);
        userArrayList = new ArrayList<>();
        userAdapter = new UserAdapter(this, R.layout.dong_nhiem_vu, userArrayList);
        lvTaiKhoan.setAdapter(userAdapter);
        getDataUser();

    }

    private void getDataUser() {
        APIService.apiService.getUsers().enqueue(new Callback<ArrayList<User>>() {
            @Override
            public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
                userArrayList = response.body();
                userAdapter = new UserAdapter(ManHinhChinhTaiKhoan.this, R.layout.dong_nguoi_dung, userArrayList);
                lvTaiKhoan.setAdapter(userAdapter);
                userAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<User>> call, Throwable t) {
                Toast.makeText(ManHinhChinhTaiKhoan.this, "Call API that bai", Toast.LENGTH_SHORT).show();
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.them_tai_khoan, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.themTaiKhoan) {
            themTaiKhoan();
        }
        return super.onOptionsItemSelected(item);
    }

    private void themTaiKhoan() {
        Intent intent = new Intent(ManHinhChinhTaiKhoan.this, ManHinhThemTaiKhoan.class);
        Bundle bundle = new Bundle();
        bundle.putString("action", "them");
        intent.putExtra("bundle", bundle);
        startActivityForResult(intent, 200);
    }
    public void suaTaiKhoan(User user) {
        Intent intent = new Intent(ManHinhChinhTaiKhoan.this, ManHinhThemTaiKhoan.class);
        Bundle bundle = new Bundle();
        bundle.putString("action", "sua");
        bundle.putSerializable("user", user);
        intent.putExtra("bundle", bundle);
        startActivityForResult(intent, 200);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 200) {
            getDataUser();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void deleteUser(User user) {
        APIService.apiService.deleteUser(user.getId()).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("ececeeee", t.getMessage());
            }
        });
        APIService.apiService.deleteClientUsername(user.getUsername()).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ManHinhChinhTaiKhoan.this, "Xoá Thành Công", Toast.LENGTH_SHORT).show();


                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("ececeeee", t.getMessage());
            }
        });

        getDataUser();
    }

    public void DialogXoa(User user) {
        AlertDialog.Builder dialogXoa = new AlertDialog.Builder(this);
        dialogXoa.setMessage("Xóa user: '" + user.getName() + "' ?");
        dialogXoa.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteUser(user);
//                Toast.makeText(ManHinhChinhTaiKhoan.this, "Xóa thành công!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        dialogXoa.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialogXoa.show();
    }


}
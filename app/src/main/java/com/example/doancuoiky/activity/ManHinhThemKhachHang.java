package com.example.doancuoiky.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doancuoiky.API.APIService;
import com.example.doancuoiky.R;
import com.example.doancuoiky.model.Client;
import com.example.doancuoiky.model.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManHinhThemKhachHang extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_them_khach_hang);

        init();
    }

    private void init() {
        Bundle bundle = getIntent().getBundleExtra("bundle");
        String action = bundle.getString("action");


        TextView tvTieuDe = findViewById(R.id.tvTieuDeThemKhachHang);
        tvTieuDe.setText(action.equals("them") ? "Thêm Khách Hàng" : "Cập nhật khách hàng");
        EditText etUsername = findViewById(R.id.etThemUsernameKhachHang);
        EditText etPassword = findViewById(R.id.etThemPasswordKhachHang);
        EditText etTenAdd = findViewById(R.id.etThemTenKhachHang);
        EditText etSDTAdd = findViewById(R.id.etThemSDTKhachHang);
        EditText etDiachiAdd = findViewById(R.id.etThemDiachiKhachHang);
        Button btnThem = findViewById(R.id.btnThem);
        btnThem.setText(action.equals("them") ? "Thêm" : "Cập nhật");

        if (action.equals("sua")) {
            Client oldClient = (Client) bundle.getSerializable("khachhang");
            etTenAdd.setText(oldClient.getCompany());
            etSDTAdd.setText(oldClient.getPhone());
            etDiachiAdd.setText(oldClient.getAddress());
            etUsername.setText(oldClient.getUsername());
            etUsername.setEnabled(false);

            APIService.apiService.getUserKH(oldClient.getUsername()).enqueue(new Callback<ArrayList<User>>() {
                @Override
                public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
                    User user = response.body().get(0);
                    etPassword.setText(user.getPassword());
                }

                @Override
                public void onFailure(Call<ArrayList<User>> call, Throwable t) {
                    Toast.makeText(ManHinhThemKhachHang.this, "Call API that bai" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        Button btnHuy = findViewById(R.id.btnHuy);
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String tenKhachHang = etTenAdd.getText().toString().trim();
                String SDTKhachHang = etSDTAdd.getText().toString().trim();
                String DiachiKhachHang = etDiachiAdd.getText().toString().trim();
                if (username.equals("")) {
                    Toast.makeText(ManHinhThemKhachHang.this, "Vui lòng nhập tài khoản!", Toast.LENGTH_SHORT).show();
                    etUsername.requestFocus();
                } else if (password.equals("")) {
                    Toast.makeText(ManHinhThemKhachHang.this, "Vui lòng nhập mật khẩu!", Toast.LENGTH_SHORT).show();
                    etPassword.requestFocus();
                } else if (tenKhachHang.equals("")) {
                    Toast.makeText(ManHinhThemKhachHang.this, "Vui lòng nhập tên!", Toast.LENGTH_SHORT).show();
                    etTenAdd.requestFocus();
                } else if (SDTKhachHang.equals("")) {
                    Toast.makeText(ManHinhThemKhachHang.this, "Vui lòng nhập SDT!", Toast.LENGTH_SHORT).show();
                    etSDTAdd.requestFocus();
                } else if (DiachiKhachHang.equals("")) {
                    Toast.makeText(ManHinhThemKhachHang.this, "Vui lòng nhập địa chỉ!", Toast.LENGTH_SHORT).show();
                    etDiachiAdd.requestFocus();
                } else if (action.equals("them")) {
                    User user = new User();
                    user.setId(0);
                    user.setRole(null);
                    user.setUsername(username);
                    user.setPassword(password);
                    user.setName(tenKhachHang);
                    user.setAddress(DiachiKhachHang);

                    addUser(user, 2);

                    Client client = new Client();
                    client.setClientid(0);
                    client.setCompany(tenKhachHang);
                    client.setPhone(SDTKhachHang);
                    client.setAddress(DiachiKhachHang);
                    client.setUsername(username);

                    addClient(client);
                    Toast.makeText(ManHinhThemKhachHang.this, "Thêm Mới Thành Công", Toast.LENGTH_SHORT).show();
                    finish();
//                    Toast.makeText(ManHinhNhiemVu.this, "Thêm thành công!" + client.toString(), Toast.LENGTH_SHORT).show();
                } else {

                    APIService.apiService.getUserKH(username).enqueue(new Callback<ArrayList<User>>() {
                        @Override
                        public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
                            User user = response.body().get(0);
                            user.setRole(null);
                            user.setUsername(username);
                            user.setPassword(password);
                            user.setName(tenKhachHang);
                            user.setAddress(DiachiKhachHang);

                            addUser(user, 2);
                        }

                        @Override
                        public void onFailure(Call<ArrayList<User>> call, Throwable t) {
                            Toast.makeText(ManHinhThemKhachHang.this, "Call API that bai" + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });


                    Client client = new Client();
                    Client oldClient = (Client) bundle.getSerializable("khachhang");
                    client.setClientid(oldClient.getClientid());
                    client.setCompany(tenKhachHang);
                    client.setPhone(SDTKhachHang);
                    client.setAddress(DiachiKhachHang);
                    client.setUsername(username);

                    addClient(client);
//                    Toast.makeText(ManHinhNhiemVu.this, "Thêm thành công!" + client.toString(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(ManHinhThemKhachHang.this, "Cập Nhật Thành Công", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }

    private void addUser(User user, int idRole) {
        APIService.apiService.addUser(user, idRole).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
//                    Toast.makeText(ManHinhThemKhachHang.this, "Thêm Mới Thành Công", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("ececeeee", t.getMessage());
            }
        });
    }

    private void addClient(Client client) {
        APIService.apiService.addClient(client).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("ececeeee", t.getMessage());
            }
        });
    }
}
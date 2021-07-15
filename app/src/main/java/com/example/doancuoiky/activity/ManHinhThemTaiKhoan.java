package com.example.doancuoiky.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doancuoiky.API.APIService;
import com.example.doancuoiky.R;
import com.example.doancuoiky.adapter.RoleSpinnerAdapter;
import com.example.doancuoiky.model.Role;
import com.example.doancuoiky.model.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManHinhThemTaiKhoan extends AppCompatActivity {

    RoleSpinnerAdapter roleSpinnerAdapter;
    ArrayList<Role> roles;
    Spinner spnRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_them_tai_khoan);

        init();
    }

    private void init() {
        Bundle bundle = getIntent().getBundleExtra("bundle");
        Boolean isThem = bundle.getString("action").equals("them");

        spnRole = findViewById(R.id.spnRole);
        getDataRole();

        EditText etUsername = findViewById(R.id.etusername);
        EditText etPassword = findViewById(R.id.etPassword);
        EditText etTen = findViewById(R.id.etName);
        EditText etDiaChi = findViewById(R.id.etDiachi);

        Button btnThem = findViewById(R.id.btnThem);
        Button btnHuy = findViewById(R.id.btnHuy);

        if (!isThem) {
            User oldUser = (User) bundle.getSerializable("user");

            TextView tvTieuDe = findViewById(R.id.tvTieuDeThemTaiKhoan);
            tvTieuDe.setText("Cập nhật tài khoản");
            etUsername.setText(oldUser.getUsername());
            etUsername.setEnabled(false);
            etPassword.setText(oldUser.getPassword());
            etTen.setText(oldUser.getName());
            etDiaChi.setText(oldUser.getAddress());
//            if (oldUser.getRole().getId() == 2) {
                spnRole.setVisibility(View.INVISIBLE);
                View layoutRole = findViewById(R.id.layoutChucVu);
                layoutRole.setVisibility(View.GONE);
//            }

            APIService.apiService.getRoles().enqueue(new Callback<ArrayList<Role>>() {
                @Override
                public void onResponse(Call<ArrayList<Role>> call, Response<ArrayList<Role>> response) {
                    roles = response.body();
                    for (int i = 0; i < roles.size(); i++) {
                        if (roles.get(i).getId() == oldUser.getRole().getId())
                            spnRole.setSelection(i);
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<Role>> call, Throwable t) {
                    Log.e("loi API:", t.getMessage());
                }
            });
            btnThem.setText("Cập nhật");
        }

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
                String ten = etTen.getText().toString().trim();
                String diaChi = etDiaChi.getText().toString().trim();
                if (username.equals("")) {
                    Toast.makeText(ManHinhThemTaiKhoan.this, "Vui lòng nhập tài khoản!", Toast.LENGTH_SHORT).show();
                    etUsername.requestFocus();
                } else if (password.equals("")) {
                    Toast.makeText(ManHinhThemTaiKhoan.this, "Vui lòng nhập mật khẩu!", Toast.LENGTH_SHORT).show();
                    etPassword.requestFocus();
                } else if (ten.equals("")) {
                    Toast.makeText(ManHinhThemTaiKhoan.this, "Vui lòng nhập tên!", Toast.LENGTH_SHORT).show();
                    etTen.requestFocus();
                } else if (diaChi.equals("")) {
                    Toast.makeText(ManHinhThemTaiKhoan.this, "Vui lòng nhập địa chỉ!", Toast.LENGTH_SHORT).show();
                    etDiaChi.requestFocus();
                } else {
                    Role role = roles.get(spnRole.getSelectedItemPosition());
                    User user = new User();
                    if (isThem) {
                        user.setId(0);
                        user.setUsername(username);
                    } else {
                        user = (User) bundle.getSerializable("user");
                        role = user.getRole();
                    }
                    user.setRole(null);
                    user.setPassword(password);
                    user.setName(ten);
                    user.setAddress(diaChi);

                    addUser(user, role.getId());
                    finish();

                }
            }
        });
    }

    private void getDataRole() {
        APIService.apiService.getRoles().enqueue(new Callback<ArrayList<Role>>() {
            @Override
            public void onResponse(Call<ArrayList<Role>> call, Response<ArrayList<Role>> response) {
                roles = response.body();
                for (int i = 0; i < roles.size(); i++) {
                    if (roles.get(i).getId() == 2) {
                        roles.remove(roles.get(i));
                    }
                }
                roleSpinnerAdapter = new RoleSpinnerAdapter(ManHinhThemTaiKhoan.this, R.layout.dong_role_spinner, roles);
                spnRole.setAdapter(roleSpinnerAdapter);
                roleSpinnerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<Role>> call, Throwable t) {
                Toast.makeText(ManHinhThemTaiKhoan.this, "Call API that bai", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addUser(User user, int idRole) {
        APIService.apiService.addUser(user, idRole).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ManHinhThemTaiKhoan.this, "Thành Công", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("ececeeee", t.getMessage());
            }
        });
    }
}
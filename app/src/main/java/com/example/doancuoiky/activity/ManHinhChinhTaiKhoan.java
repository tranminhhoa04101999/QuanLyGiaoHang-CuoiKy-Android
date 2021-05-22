package com.example.doancuoiky.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.doancuoiky.API.APIService;
import com.example.doancuoiky.R;
import com.example.doancuoiky.adapter.ClientSpinnerAdapter;
import com.example.doancuoiky.adapter.RoleSpinnerAdapter;
import com.example.doancuoiky.adapter.UserAdapter;
import com.example.doancuoiky.model.Client;
import com.example.doancuoiky.model.Role;
import com.example.doancuoiky.model.Task;
import com.example.doancuoiky.model.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManHinhChinhTaiKhoan extends AppCompatActivity {
    ListView lvTaiKhoan;
    Spinner spnRole;
    ArrayList<User> userArrayList;
    UserAdapter userAdapter;
    RoleSpinnerAdapter roleSpinnerAdapter;
    ArrayList<Role> roles;
    User user = new User();

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

    private void getDataRole() {
        APIService.apiService.getRoles().enqueue(new Callback<ArrayList<Role>>() {
            @Override
            public void onResponse(Call<ArrayList<Role>> call, Response<ArrayList<Role>> response) {
                roles = response.body();
                roleSpinnerAdapter = new RoleSpinnerAdapter(ManHinhChinhTaiKhoan.this, R.layout.dong_role_spinner, roles);
                spnRole.setAdapter(roleSpinnerAdapter);
                roleSpinnerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<Role>> call, Throwable t) {
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
            DialogThem();

        }
        return super.onOptionsItemSelected(item);
    }

    private void DialogThem() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_them_tai_khoan);

        spnRole = dialog.findViewById(R.id.spnRole);
        getDataRole();

        EditText etUsername = dialog.findViewById(R.id.etusername);
        EditText etPassword = dialog.findViewById(R.id.etPassword);
        EditText etTen = dialog.findViewById(R.id.etName);
        EditText etDiaChi = dialog.findViewById(R.id.etDiachi);

        Button btnThem = dialog.findViewById(R.id.btnThem);
        Button btnHuy = dialog.findViewById(R.id.btnHuy);


        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setCanceledOnTouchOutside(false);

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String name = etTen.getText().toString().trim();
                String address = etDiaChi.getText().toString().trim();
                if (etUsername.equals("")) {
                    Toast.makeText(ManHinhChinhTaiKhoan.this, "Vui lòng nhập username!", Toast.LENGTH_SHORT).show();
                    etUsername.requestFocus();
                } else if (etPassword.equals("")) {
                    Toast.makeText(ManHinhChinhTaiKhoan.this, "Vui lòng nhập password!", Toast.LENGTH_SHORT).show();
                    etPassword.requestFocus();

                }
                else if (etTen.equals("")) {
                    Toast.makeText(ManHinhChinhTaiKhoan.this, "Vui lòng nhập tên!", Toast.LENGTH_SHORT).show();
                    etTen.requestFocus();

                }
                else if (etDiaChi.equals("")) {
                    Toast.makeText(ManHinhChinhTaiKhoan.this, "Vui lòng nhập địa chỉ!", Toast.LENGTH_SHORT).show();
                    etDiaChi.requestFocus();

                }else {
                    Role role = roles.get(spnRole.getSelectedItemPosition());
                    user.setId(0);
                    user.setRole(null);
                    user.setUsername(username);
                    user.setPassword(password);
                    user.setName(name);
                    user.setAddress(address);

                    addUser(user, role.getId());
//                    Toast.makeText(ManHinhNhiemVu.this, "Thêm thành công!" + client.toString(), Toast.LENGTH_SHORT).show();
                    dialog.dismiss();

                }
            }
        });
        dialog.show();
    }

    private void addUser(User user, int idRole) {
        APIService.apiService.addUser(user, idRole).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ManHinhChinhTaiKhoan.this, "Thêm Mới Thành Công", Toast.LENGTH_SHORT).show();
                    getDataUser();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("ececeeee", t.getMessage());
            }
        });
    }

    private void deleteUser(int id) {
        APIService.apiService.deleteUser(id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ManHinhChinhTaiKhoan.this, "Xoá Thành Công", Toast.LENGTH_SHORT).show();
                    getDataUser();

                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("ececeeee", t.getMessage());
            }
        });
    }

    public void DialogSua(User user) {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_them_tai_khoan);

        spnRole = dialog.findViewById(R.id.spnRole);
        getDataRole();

        EditText etUsername = dialog.findViewById(R.id.etusername);
        etUsername.setText(user.getUsername());
        EditText etPassword = dialog.findViewById(R.id.etPassword);
        etPassword.setText(user.getPassword());
        EditText etTen = dialog.findViewById(R.id.etName);
        etTen.setText(user.getName());
        EditText etDiaChi = dialog.findViewById(R.id.etDiachi);
        etDiaChi.setText(user.getAddress());

        Button btnThem = dialog.findViewById(R.id.btnThem);
        Button btnHuy = dialog.findViewById(R.id.btnHuy);


        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setCanceledOnTouchOutside(false);

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String name = etTen.getText().toString().trim();
                String address = etDiaChi.getText().toString().trim();
                if (etUsername.equals("")) {
                    Toast.makeText(ManHinhChinhTaiKhoan.this, "Vui lòng nhập username!", Toast.LENGTH_SHORT).show();
                    etUsername.requestFocus();
                } else if (etPassword.equals("")) {
                    Toast.makeText(ManHinhChinhTaiKhoan.this, "Vui lòng nhập password!", Toast.LENGTH_SHORT).show();
                    etPassword.requestFocus();

                }
                else if (etTen.equals("")) {
                    Toast.makeText(ManHinhChinhTaiKhoan.this, "Vui lòng nhập tên!", Toast.LENGTH_SHORT).show();
                    etTen.requestFocus();

                }
                else if (etDiaChi.equals("")) {
                    Toast.makeText(ManHinhChinhTaiKhoan.this, "Vui lòng nhập địa chỉ!", Toast.LENGTH_SHORT).show();
                    etDiaChi.requestFocus();

                }else {
                    Role role = roles.get(spnRole.getSelectedItemPosition());
                    user.setRole(null);
                    user.setUsername(username);
                    user.setPassword(password);
                    user.setName(name);
                    user.setAddress(address);

                    addUser(user, role.getId());
//                    Toast.makeText(ManHinhNhiemVu.this, "Thêm thành công!" + client.toString(), Toast.LENGTH_SHORT).show();
                    dialog.dismiss();

                }
            }
        });
        dialog.show();
    }

    public void DialogXoa(int id) {
        AlertDialog.Builder dialogXoa = new AlertDialog.Builder(this);
        dialogXoa.setMessage("Xóa user: '" + id + "' ?");
        dialogXoa.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteUser(id);
                Toast.makeText(ManHinhChinhTaiKhoan.this, "Xóa thành công!", Toast.LENGTH_SHORT).show();
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
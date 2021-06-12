package com.example.doancuoiky.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.doancuoiky.API.APIService;
import com.example.doancuoiky.R;
import com.example.doancuoiky.model.Role;
import com.example.doancuoiky.model.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    EditText edt_username,edt_password;
    Button btn_dangnhap;
    ArrayList<Role> roles;
    User mUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setControl();

        btn_dangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edt_username.getText().toString().equals("")){
                    Toast.makeText(Login.this, "Không được để trống Username", Toast.LENGTH_SHORT).show();
                    edt_username.requestFocus();
                    return;
                }else if(edt_password.getText().toString().equals("")){
                    Toast.makeText(Login.this, "Không được để trống Password", Toast.LENGTH_SHORT).show();
                    edt_password.requestFocus();
                    return;
                }
                String username = edt_username.getText().toString();
                String password = edt_password.getText().toString();

                clickCallApi_login(username,password);
            }
        });
    }

    private void clickCallApi_login(String username,String password) {
        APIService.apiService.getUser(username,password).enqueue(new Callback<ArrayList<User>>() {
            @Override
            public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {

                ArrayList<User> userArrayList = response.body();
                if(userArrayList.isEmpty() || userArrayList == null){
                    Toast.makeText(Login.this, "Sai thông tin đăng nhặp", Toast.LENGTH_LONG).show();
                    return;
                }
                else{
                    mUser = userArrayList.get(0);

                    // Kiểm tra coi role gì thì cho vào trang đó  (1 : tai xe, 2 :khachhang, 3:admin)
                    int checkid = mUser.getRole().getId();
                    Intent intent = new Intent(Login.this, ManHinhChinhTaiXe.class);
                    if(checkid == 2){
                        intent = new Intent(Login.this, ManHinhKhachHang.class);
                    }else if(checkid == 3){
                        intent = new Intent(Login.this,ManHinhChinhAdmin.class);
                    }


                    Bundle bundle = new Bundle();
                    bundle.putSerializable("objectUser",mUser);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }

            }

            @Override
            public void onFailure(Call<ArrayList<User>> call, Throwable t) {
                Toast.makeText(Login.this, "Call API thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void checkRole(){

    }

    private void setControl() {
        edt_username = findViewById(R.id.login_username);
        edt_password = findViewById(R.id.login_password);
        btn_dangnhap = findViewById(R.id.btn_dangnhap);
    }

}
package com.example.doancuoiky.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.doancuoiky.API.APIService;
import com.example.doancuoiky.R;
import com.example.doancuoiky.model.Client;
import com.example.doancuoiky.model.Role;
import com.example.doancuoiky.model.Task;
import com.example.doancuoiky.model.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    EditText edt_username,edt_password;
    Button btn_dangnhap;
    ArrayList<Role> roles;
    ArrayList<Task> taskArrayList;
    User mUser;
    Task mTask;
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
                    return;
                }else if(edt_password.getText().toString().equals("")){
                    Toast.makeText(Login.this, "Không được để trống Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                String username = edt_username.getText().toString();
                String password = edt_password.getText().toString();

                //clickCallApi_login(username,password);
                 //check(taskArrayList);
                //Toast.makeText(Login.this, taskArrayList.get(0).toString(), Toast.LENGTH_SHORT).show();

//                Task task = new Task(0,"2021-06-06","2021-06-06",false,false,false,null);
//                addTask(task,1);

                deleteTask(18);

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
                        intent = new Intent(Login.this, ManHinhChinhKhachHang.class);
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

    private void addTask(Task task,int idClient){
        APIService.apiService.addTask(task,idClient).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    Toast.makeText(Login.this, "Thêm Mới Thành Công", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("ececeeee", t.getMessage());
            }
        });
    }

    private void deleteTask(int id){
        APIService.apiService.deleteTask(id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    Toast.makeText(Login.this, "Xoá Thành Công", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("ececeeee", t.getMessage());
            }
        });
    }

    public void getsss(){
        Call<ArrayList<Task>> call = APIService.apiService.getTasks();

    }

    private void setControl() {
        edt_username = findViewById(R.id.login_username);
        edt_password = findViewById(R.id.login_password);
        btn_dangnhap = findViewById(R.id.btn_dangnhap);
    }

}
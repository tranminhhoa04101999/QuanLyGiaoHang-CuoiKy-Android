package com.example.doancuoiky.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.doancuoiky.API.APIService;
import com.example.doancuoiky.R;
import com.example.doancuoiky.adapter.KhachHangAdapter;
import com.example.doancuoiky.model.Client;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManHinhChinhKhachHang extends AppCompatActivity {
    ListView lvClient;
    KhachHangAdapter khachHangAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_chinh_khach_hang);

        lvClient = findViewById(R.id.lvClient);
        getDataClient();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.them_khach_hang, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.themKhachHang) {
            themKhachHang();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 200) {
            getDataClient();
        }
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void deleteClient(int id, String username) {
        APIService.apiService.deleteClient(id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(ManHinhChinhKhachHang.this, "That bai:\n" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        APIService.apiService.deleteUserUsername(username).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ManHinhChinhKhachHang.this, "Thành Công", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(ManHinhChinhKhachHang.this, "Thất bại:\n" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getDataClient() {
        APIService.apiService.getClients().enqueue(new Callback<ArrayList<Client>>() {
            @Override
            public void onResponse(Call<ArrayList<Client>> call, Response<ArrayList<Client>> response) {
                ArrayList<Client> clientArrayList = response.body();
                khachHangAdapter = new KhachHangAdapter(ManHinhChinhKhachHang.this, R.layout.dong_khach_hang, clientArrayList);
                lvClient.setAdapter(khachHangAdapter);
                khachHangAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<Client>> call, Throwable t) {
                Toast.makeText(ManHinhChinhKhachHang.this, "Call API that bai", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void themKhachHang() {
        Intent intent = new Intent(ManHinhChinhKhachHang.this, ManHinhThemKhachHang.class);
        Bundle bundle = new Bundle();
        bundle.putString("action", "them");
        intent.putExtra("bundle", bundle);
        startActivityForResult(intent, 200);
    }

    public void suaKhachHang(Client khachHang) {
        Intent intent = new Intent(ManHinhChinhKhachHang.this, ManHinhThemKhachHang.class);
        Bundle bundle = new Bundle();
        bundle.putString("action", "sua");
        bundle.putSerializable("khachhang", khachHang);
        intent.putExtra("bundle", bundle);
        startActivityForResult(intent, 200);
    }

    public void DialogXoa(Client client) {
        AlertDialog.Builder dialogXoa = new AlertDialog.Builder(this);
        dialogXoa.setMessage("Bạn có muốn xóa client: '" + client.getCompany() + "' ?");
        dialogXoa.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteClient(client.getClientid(), client.getUsername());
                dialog.dismiss();
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                getDataClient();
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
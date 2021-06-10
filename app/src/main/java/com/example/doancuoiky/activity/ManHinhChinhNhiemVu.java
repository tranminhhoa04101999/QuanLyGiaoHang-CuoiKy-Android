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
import com.example.doancuoiky.adapter.TaskAdapter;
import com.example.doancuoiky.model.Task;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManHinhChinhNhiemVu extends AppCompatActivity {
    ListView lvTask;
    TaskAdapter taskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_chinh_nhiem_vu);
        lvTask = findViewById(R.id.lvTask);

        getDataTask();
    }

    private void getDataTask() {
        APIService.apiService.getTasks().enqueue(new Callback<ArrayList<Task>>() {
            @Override
            public void onResponse(Call<ArrayList<Task>> call, Response<ArrayList<Task>> response) {
                ArrayList<Task> taskArrayList2 = response.body();
                taskAdapter = new TaskAdapter(ManHinhChinhNhiemVu.this, R.layout.dong_nhiem_vu, taskArrayList2);
                lvTask.setAdapter(taskAdapter);
                taskAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<Task>> call, Throwable t) {
                Toast.makeText(ManHinhChinhNhiemVu.this, "Call API that bai", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.them_nhiem_vu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.themNhiemVu) {
            themNhiemVu();
        }
        return super.onOptionsItemSelected(item);
    }

    private void themNhiemVu() {
        Intent intent = new Intent(ManHinhChinhNhiemVu.this, ManHinhThemNhiemVu.class);
        Bundle bundle = new Bundle();
        bundle.putString("action", "them");
        intent.putExtra("bundle", bundle);
        startActivityForResult(intent, 200);
    }

    public void suaNhiemVu(Task task) {
        Intent intent = new Intent(ManHinhChinhNhiemVu.this, ManHinhThemNhiemVu.class);
        Bundle bundle = new Bundle();
        bundle.putString("action", "sua");
        bundle.putSerializable("task", task);
        intent.putExtra("bundle", bundle);
        startActivityForResult(intent, 200);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 200) {
            getDataTask();
        }
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void deleteTask(int id) {
        APIService.apiService.deleteTask(id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ManHinhChinhNhiemVu.this, "Xoá Thành Công", Toast.LENGTH_SHORT).show();
                    getDataTask();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("ececeeee", t.getMessage());
            }
        });
    }

    public void DialogXoa(int id) {
        AlertDialog.Builder dialogXoa = new AlertDialog.Builder(this);
        dialogXoa.setMessage("Xóa task: '" + id + "' ?");
        dialogXoa.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteTask(id);
                dialog.dismiss();
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                getDataTask();
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
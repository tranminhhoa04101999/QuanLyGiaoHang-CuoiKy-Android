package com.example.doancuoiky.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doancuoiky.API.APIService;
import com.example.doancuoiky.R;
import com.example.doancuoiky.adapter.TaiXeAdapter;
import com.example.doancuoiky.adapter.UserAdapter;
import com.example.doancuoiky.model.Client;
import com.example.doancuoiky.model.Task;
import com.example.doancuoiky.model.TaskDetail;
import com.example.doancuoiky.model.User;

import org.w3c.dom.Text;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManHinhThemPhanCong extends AppCompatActivity {
    Spinner spnTaixe;
    ArrayList<TaskDetail> taskDetails;
    TaiXeAdapter taiXeAdapter;
    ArrayList<User> dSTaiXe;
    TaskDetail taskDetail;
    User taiXe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_them_phan_cong);

        spnTaixe = findViewById(R.id.spnPCTaiXe);
        getDataTaixe();
        init();
    }

    void getDataTaixe() {
        APIService.apiService.getDSTaiXe().enqueue(new Callback<ArrayList<User>>() {
            @Override
            public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
                dSTaiXe = response.body();
                taiXeAdapter = new TaiXeAdapter(ManHinhThemPhanCong.this, R.layout.dong_tai_xe, dSTaiXe);
                spnTaixe.setAdapter(taiXeAdapter);
                taiXeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<User>> call, Throwable t) {

            }
        });
    }

    private void init() {
        Bundle bundle = getIntent().getBundleExtra("bundle");
        Boolean isThem = bundle.getString("action").equals("them");
        Task task = (Task) bundle.getSerializable("task");

        TextView tvPickup = findViewById(R.id.tvPCPickup);
        tvPickup.setText(task.getPickup());
        TextView tvTieuDe = findViewById(R.id.tvTieuDeThemPhanCong);
        TextView tvClient = findViewById(R.id.tvPCKH);
        tvClient.setText(task.getClient().getCompany());

        EditText etGhiChu = findViewById(R.id.etPCGhiChu);

        Button btnThem = findViewById(R.id.btnThem);
        Button btnHuy = findViewById(R.id.btnHuy);
        Button btnXoa = findViewById(R.id.btnXoaPhanCong);

        if (isThem) {
            tvTieuDe.setText("Thêm Phân Công");
            btnXoa.setVisibility(View.GONE);
        } else {
            btnThem.setText("Cập nhật");
            tvTieuDe.setText("Chỉnh Sửa Phân Công");
            APIService.apiService.getTaskDetails().enqueue(new Callback<ArrayList<TaskDetail>>() {
                @Override
                public void onResponse(Call<ArrayList<TaskDetail>> call, Response<ArrayList<TaskDetail>> response) {
                    ArrayList<TaskDetail> taskDetails = response.body();
                    for (int i = 0; i < taskDetails.size(); i++) {
                        if (taskDetails.get(i).getTask().getId() == task.getId()) {
                            taskDetail = taskDetails.get(i);
                            etGhiChu.setText(taskDetail.getTask_note());
//                            Log.e("a", etGhiChu.getText().toString());
                            APIService.apiService.getDSTaiXe().enqueue(new Callback<ArrayList<User>>() {
                                @Override
                                public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
                                    dSTaiXe = response.body();
                                    for (int i = 0; i < dSTaiXe.size(); i++) {
                                        if (dSTaiXe.get(i).getId() == taskDetail.getUser().getId())
                                            spnTaixe.setSelection(i);
                                    }

                                }

                                @Override
                                public void onFailure(Call<ArrayList<User>> call, Throwable t) {

                                }
                            });
//                            Log.e("aaaaa", "Thanh cong " + taskDetail.toString());

                            break;
                        }
                    }

                }

                @Override
                public void onFailure(Call<ArrayList<TaskDetail>> call, Throwable t) {
                    Log.e("aaaaa", t.getMessage());
                }
            });

            btnXoa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteTaskDetail(taskDetail.getId());
                    task.setApprove(false);
                    updateTask(task, task.getClient().getClientid());
                    finish();
                }
            });
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
                String ghiChu = etGhiChu.getText().toString().trim();

                taiXe = dSTaiXe.get(spnTaixe.getSelectedItemPosition());

                if (isThem) {
                    taskDetail = new TaskDetail();
                    taskDetail.setId(0);
                    taskDetail.setChat("");
                    taskDetail.setUser(taiXe);
                } else {
                    if (taskDetail.getUser().getId() != taiXe.getId()) {
                        taskDetail.setUser(taiXe);
                        taskDetail.setChat("");
                    }
                }
                task.setApprove(true);
                updateTask(task, task.getClient().getClientid());
                taskDetail.setTask(task);
                taskDetail.setTask_note(ghiChu);

                addTaskDetail(taskDetail);
                finish();
            }
        });


    }

    void addTaskDetail(TaskDetail taskDetail) {
        APIService.apiService.addTaskDetail(taskDetail, taskDetail.getUser().getId()).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ManHinhThemPhanCong.this, "Thành công!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(ManHinhThemPhanCong.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateTask(Task task, int idClient) {
        APIService.apiService.addTask(task, idClient).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
//                    Toast.makeText(ManHinhThemPhanCong.this, "Thành Công", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("ececeeee", t.getMessage());
            }
        });
    }

    private void deleteTaskDetail(int id) {
        APIService.apiService.deleteTaskDetail(id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ManHinhThemPhanCong.this, "Xóa thành công!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(ManHinhThemPhanCong.this, "That bai: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

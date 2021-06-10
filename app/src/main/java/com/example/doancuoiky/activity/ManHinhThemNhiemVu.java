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
import com.example.doancuoiky.adapter.ClientSpinnerAdapter;
import com.example.doancuoiky.model.Client;
import com.example.doancuoiky.model.Task;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManHinhThemNhiemVu extends AppCompatActivity {

    ClientSpinnerAdapter clientSpinnerAdapter;
    ArrayList<Client> clients;
    Spinner spnKH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_them_nhiem_vu);

        init();
    }

    private void init() {
        Bundle bundle = getIntent().getBundleExtra("bundle");
        Boolean isThem = bundle.getString("action").equals("them");

        spnKH = findViewById(R.id.spnKH);
        getDataClient();

        EditText etPickup = findViewById(R.id.etPickup);
        EditText etDropoff = findViewById(R.id.etDropoff);

        CheckBox cbApprove = findViewById(R.id.cbApprovee);
        CheckBox cbPublic = findViewById(R.id.cbPublice);
        CheckBox cbCancel = findViewById(R.id.cbCancele);

        Button btnThem = findViewById(R.id.btnThem);
        Button btnHuy = findViewById(R.id.btnHuy);

        if (!isThem) {
            TextView tvTieuDe = findViewById(R.id.tvTieuDeThemNhiemVu);
            tvTieuDe.setText("Cập nhật nhiệm vụ");
            Task oldTask = (Task) bundle.getSerializable("task");
            etPickup.setText(oldTask.getPickup());
            etDropoff.setText(oldTask.getDropoff());
            cbApprove.setChecked(oldTask.getApprove());
            cbPublic.setChecked(oldTask.getTaskpublic());
            cbCancel.setChecked(oldTask.getCancel());
            APIService.apiService.getClients().enqueue(new Callback<ArrayList<Client>>() {
                @Override
                public void onResponse(Call<ArrayList<Client>> call, Response<ArrayList<Client>> response) {
                    clients = response.body();
                    for (int i = 0; i < clients.size(); i++) {
                        if (clients.get(i).getClientid() == oldTask.getClient().getClientid())
                            spnKH.setSelection(i);
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<Client>> call, Throwable t) {
                    Toast.makeText(ManHinhThemNhiemVu.this, "Call API that bai", Toast.LENGTH_SHORT).show();
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
                String pickupdate = etPickup.getText().toString().trim();
                String dropoffdate = etDropoff.getText().toString().trim();
                if (pickupdate.equals("")) {
                    Toast.makeText(ManHinhThemNhiemVu.this, "Vui lòng nhập ngày!", Toast.LENGTH_SHORT).show();
                    etPickup.requestFocus();
                } else if (dropoffdate.equals("")) {
                    Toast.makeText(ManHinhThemNhiemVu.this, "Vui lòng nhập ngày!", Toast.LENGTH_SHORT).show();
                    etDropoff.requestFocus();

                } else {
                    Client client = clients.get(spnKH.getSelectedItemPosition());
                    Task task = new Task();
                    if (isThem) {
                        task.setId(0);
                    } else {
                        task = (Task) bundle.getSerializable("task");
                    }
                    task.setClient(null);
                    task.setPickup(pickupdate);
                    task.setDropoff(dropoffdate);
                    task.setApprove(cbApprove.isChecked());
                    task.setTaskpublic(cbPublic.isChecked());
                    task.setCancel(cbCancel.isChecked());

                    addTask(task, client.getClientid());
                    finish();

                }
            }
        });
    }

    private void getDataClient() {
        APIService.apiService.getClients().enqueue(new Callback<ArrayList<Client>>() {
            @Override
            public void onResponse(Call<ArrayList<Client>> call, Response<ArrayList<Client>> response) {
                clients = response.body();
                clientSpinnerAdapter = new ClientSpinnerAdapter(ManHinhThemNhiemVu.this, R.layout.dong_khach_hang_spinner, clients);
                spnKH.setAdapter(clientSpinnerAdapter);
                clientSpinnerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<Client>> call, Throwable t) {
                Toast.makeText(ManHinhThemNhiemVu.this, "Call API that bai", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addTask(Task task, int idClient) {
        APIService.apiService.addTask(task, idClient).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ManHinhThemNhiemVu.this, "Thành Công", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("ececeeee", t.getMessage());
            }
        });
    }
}
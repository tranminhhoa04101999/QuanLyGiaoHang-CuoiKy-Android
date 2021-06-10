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
import com.example.doancuoiky.adapter.TaskAdapter;
import com.example.doancuoiky.model.Client;
import com.example.doancuoiky.model.Task;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManHinhChinhNhiemVu extends AppCompatActivity {
    ListView lvTask;
    Spinner spnKH;
    //    ArrayList<Task> taskArrayList;
    TaskAdapter taskAdapter;
    ClientSpinnerAdapter clientSpinnerAdapter;
    ArrayList<Client> clients;
    Task task = new Task();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_chinh_nhiem_vu);
        lvTask = findViewById(R.id.lvTask);
//        taskArrayList = new ArrayList<>();
//        taskAdapter = new TaskAdapter(this, R.layout.dong_nhiem_vu, taskArrayList);
//        lvTask.setAdapter(taskAdapter);
        getDataTask();


    }


    private void getDataTask() {
        APIService.apiService.getTasks().enqueue(new Callback<ArrayList<Task>>() {
            @Override
            public void onResponse(Call<ArrayList<Task>> call, Response<ArrayList<Task>> response) {
                ArrayList<Task> taskArrayList2 = response.body();
//                Toast.makeText(ManHinhNhiemVu.this, taskArrayList2.get(0).toString(), Toast.LENGTH_SHORT).show();
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

    private void getDataClient() {
        APIService.apiService.getClients().enqueue(new Callback<ArrayList<Client>>() {
            @Override
            public void onResponse(Call<ArrayList<Client>> call, Response<ArrayList<Client>> response) {
                clients = response.body();
                clientSpinnerAdapter = new ClientSpinnerAdapter(ManHinhChinhNhiemVu.this, R.layout.dong_khach_hang_spinner, clients);
                spnKH.setAdapter(clientSpinnerAdapter);
                clientSpinnerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<Client>> call, Throwable t) {
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
            DialogThem();
        }
        return super.onOptionsItemSelected(item);
    }

    private void DialogThem() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_them_nhiem_vu);

        spnKH = dialog.findViewById(R.id.spnKH);
        getDataClient();

        EditText etPickup = dialog.findViewById(R.id.etPickup);

        EditText etDropoff = dialog.findViewById(R.id.etDropoff);

        CheckBox cbApprove = dialog.findViewById(R.id.cbApprovee);
        CheckBox cbPublic = dialog.findViewById(R.id.cbPublice);
        CheckBox cbCancel = dialog.findViewById(R.id.cbCancele);

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
                String pickupdate = etPickup.getText().toString();
                String dropoffdate = etDropoff.getText().toString();
                if (pickupdate.equals("")) {
                    Toast.makeText(ManHinhChinhNhiemVu.this, "Vui lòng nhập ngày!", Toast.LENGTH_SHORT).show();
                    etPickup.requestFocus();
                } else if (dropoffdate.equals("")) {
                    Toast.makeText(ManHinhChinhNhiemVu.this, "Vui lòng nhập ngày!", Toast.LENGTH_SHORT).show();
                    etDropoff.requestFocus();

                } else {
                    Client client = clients.get(spnKH.getSelectedItemPosition());
                    task.setId(0);
                    task.setClient(null);
                    task.setPickup(pickupdate);
                    task.setDropoff(dropoffdate);
                    task.setApprove(cbApprove.isChecked());
                    task.setTaskpublic(cbPublic.isChecked());
                    task.setCancel(cbCancel.isChecked());

                    addTask(task, client.getClientid());
//                    Toast.makeText(ManHinhNhiemVu.this, "Thêm thành công!" + client.toString(), Toast.LENGTH_SHORT).show();
                    dialog.dismiss();

                }
            }
        });
        dialog.show();
    }

    private void addTask(Task task, int idClient) {
        APIService.apiService.addTask(task, idClient).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ManHinhChinhNhiemVu.this, "Thêm Mới Thành Công", Toast.LENGTH_SHORT).show();
                    getDataTask();
                }

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("ececeeee", t.getMessage());
            }
        });
    }

    private void UpdateTask(Task task, int idClient) {
        APIService.apiService.UpdateTask(task, idClient).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ManHinhChinhNhiemVu.this, "Update Task Thành Công", Toast.LENGTH_SHORT).show();
                    getDataTask();
                }

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("ececeeee", t.getMessage());
            }
        });
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

    public void DialogSua(Task task) {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_them_nhiem_vu);

        spnKH = dialog.findViewById(R.id.spnKH);
        getDataClient();
//        for (int i = 0; i < clients.size(); i++) {
//            if (clients.get(i).getClientid() == task.getClient().getClientid())
//                spnKH.setSelection(i);
//        }

        TextView tvTitle = dialog.findViewById(R.id.tvTaskDialog);
        tvTitle.setText("Cập nhật nhiệm vụ");

        EditText etPickup = dialog.findViewById(R.id.etPickup);
        etPickup.setText(task.getPickup());

        EditText etDropoff = dialog.findViewById(R.id.etDropoff);
        etDropoff.setText(task.getDropoff());

        CheckBox cbApprove = dialog.findViewById(R.id.cbApprovee);
        cbApprove.setChecked(task.getApprove());
        CheckBox cbPublic = dialog.findViewById(R.id.cbPublice);
        cbPublic.setChecked(task.getTaskpublic());
        CheckBox cbCancel = dialog.findViewById(R.id.cbCancele);
        cbCancel.setChecked(task.getCancel());

        Button btnThem = dialog.findViewById(R.id.btnThem);
        btnThem.setText("Cập nhật");
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
                String pickupdate = etPickup.getText().toString();
                String dropoffdate = etDropoff.getText().toString();
                if (pickupdate.equals("")) {
                    Toast.makeText(ManHinhChinhNhiemVu.this, "Vui lòng nhập ngày!", Toast.LENGTH_SHORT).show();
                    etPickup.requestFocus();
                } else if (dropoffdate.equals("")) {
                    Toast.makeText(ManHinhChinhNhiemVu.this, "Vui lòng nhập ngày!", Toast.LENGTH_SHORT).show();
                    etDropoff.requestFocus();

                } else {
                    Client client = clients.get(spnKH.getSelectedItemPosition());

//                    task.setId(0);
                    task.setClient(null);
                    task.setPickup(pickupdate);
                    task.setDropoff(dropoffdate);
                    task.setApprove(cbApprove.isChecked());
                    task.setTaskpublic(cbPublic.isChecked());
                    task.setCancel(cbCancel.isChecked());

                    UpdateTask(task, client.getClientid());
//                    Toast.makeText(ManHinhNhiemVu.this, "Thêm thành công!" + client.toString(), Toast.LENGTH_SHORT).show();
                    getDataTask();
                    dialog.dismiss();

                }
            }
        });
        dialog.show();
    }

    public void DialogXoa(int id) {
        AlertDialog.Builder dialogXoa = new AlertDialog.Builder(this);
        dialogXoa.setMessage("Xóa task: '" + id + "' ?");
        dialogXoa.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteTask(id);
//                Toast.makeText(ManHinhNhiemVu.this, "Xóa thành công!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
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
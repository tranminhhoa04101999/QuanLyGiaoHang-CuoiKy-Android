package com.example.doancuoiky.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.doancuoiky.API.APIService;
import com.example.doancuoiky.R;
import com.example.doancuoiky.adapter.ClientSpinnerAdapter;
import com.example.doancuoiky.adapter.KhachHangTaskAdapter;
import com.example.doancuoiky.adapter.TaiXeTaskAdapter;
import com.example.doancuoiky.model.Client;
import com.example.doancuoiky.model.Task;
import com.example.doancuoiky.model.TaskDetail;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class XemTaskKhachHangFragment extends Fragment {
    ListView lvTask;
    Spinner spnKH;
    KhachHangTaskAdapter taskAdapter;
    ClientSpinnerAdapter clientSpinnerAdapter;
    ArrayList<Client> clients;
//    Task task = new Task();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle saveInstanceState) {
        View view = inflater.inflate(R.layout.fragment_xemtask_khachhang, container, false);
        getDataTask();
        lvTask = (ListView) view.findViewById(R.id.lvTaskKhachHang);
        return view;
    }

    private void getDataTask() {
        Bundle bundle = this.getArguments();
        String username = "";
        if (bundle != null) {
            username = bundle.getString("id");
        }
        Log.e("hasdfas", username + " ");
        APIService.apiService.getTaskByUserName(username).enqueue(new Callback<ArrayList<Task>>() {
            @Override
            public void onResponse(Call<ArrayList<Task>> call, Response<ArrayList<Task>> response) {
                ArrayList<Task> ArrayList2 = response.body();
//              Toast.makeText(ManHinhNhiemVu.this, taskArrayList2.get(0).toString(), Toast.LENGTH_SHORT).show();
                taskAdapter = new KhachHangTaskAdapter(XemTaskKhachHangFragment.this, R.layout.dong_nhiem_vu, ArrayList2);
                lvTask.setAdapter(taskAdapter);
                taskAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<Task>> call, Throwable t) {
                Toast.makeText(getActivity(), "Call API that bai", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getDataClient() {
        APIService.apiService.getClients().enqueue(new Callback<ArrayList<Client>>() {
            @Override
            public void onResponse(Call<ArrayList<Client>> call, Response<ArrayList<Client>> response) {
                clients = response.body();
                clientSpinnerAdapter = new ClientSpinnerAdapter(getActivity(), R.layout.dong_khach_hang_spinner, clients);
                spnKH.setAdapter(clientSpinnerAdapter);
                clientSpinnerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<Client>> call, Throwable t) {
                Toast.makeText(getActivity(), "Call API that bai", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showDialog(int id) {
        APIService.apiService.getTaskDetailByTaskId(id).enqueue(new Callback<ArrayList<TaskDetail>>() {
            @Override
            public void onResponse(Call<ArrayList<TaskDetail>> call, Response<ArrayList<TaskDetail>> response) {
                Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.dialog_task_note);
                TextView tvGhiChu = dialog.findViewById(R.id.tvGhiChu);
                tvGhiChu.setText(response.body().get(0).getTask_note());
                Button btnChat = dialog.findViewById(R.id.btnChat);
                Button btnXemMap = dialog.findViewById(R.id.btnXemMap);

                dialog.setTitle("Add an Expense");
                dialog.setCancelable(true);
                btnChat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(), ManHinhChat.class);
                        intent.putExtra("taskid",id);
                        intent.putExtra("role","kh");
                        startActivity(intent);
                        dialog.dismiss();
                    }
                });
                btnXemMap.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(), ManHinhMapTaiXe.class);
                        intent.putExtra("taskid",id);
                        startActivity(intent);
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }

            @Override
            public void onFailure(Call<ArrayList<TaskDetail>> call, Throwable t) {

            }
        });

    }

}

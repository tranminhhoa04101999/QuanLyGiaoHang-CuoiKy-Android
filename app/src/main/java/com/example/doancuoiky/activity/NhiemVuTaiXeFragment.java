package com.example.doancuoiky.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.os.Handler;
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
import com.example.doancuoiky.adapter.TaiXeTaskAdapter;
import com.example.doancuoiky.model.Client;
import com.example.doancuoiky.model.Task;
import com.example.doancuoiky.model.TaskDetail;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NhiemVuTaiXeFragment extends Fragment {
    ListView lvTask;
    Spinner spnKH;
    TaiXeTaskAdapter taskAdapter;
    ClientSpinnerAdapter clientSpinnerAdapter;
    ArrayList<Client> clients;

    Handler handler = new Handler();
    Runnable runnable;
    int delay = 500;


    @Override
    public void onResume() {
        handler.postDelayed( runnable = new Runnable() {
            public void run() {
                //do something
                getDataTask();
                handler.postDelayed(runnable, delay);
            }
        }, delay);
        super.onResume();
    }

    @Override
    public void onPause() {
        handler.removeCallbacks(runnable);
        super.onPause();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle saveInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nhiemvu_taixe, container, false);
        getDataTask();
        lvTask = (ListView) view.findViewById(R.id.lvTaskTaiXe);
        return view;
    }

    private void getDataTask() {
        Bundle bundle = this.getArguments();
        int idtaixe = 0;
        if (bundle != null) {
            idtaixe = bundle.getInt("id");
        }
        APIService.apiService.getTaskdetailByIdtaixe(idtaixe).enqueue(new Callback<ArrayList<TaskDetail>>() {
            @Override
            public void onResponse(Call<ArrayList<TaskDetail>> call, Response<ArrayList<TaskDetail>> response) {
                ArrayList<TaskDetail> ArrayList2 = response.body();
//              Toast.makeText(ManHinhNhiemVu.this, taskArrayList2.get(0).toString(), Toast.LENGTH_SHORT).show();
                List<Task> tasks = new ArrayList<>();

                for (int i = 0; i < ArrayList2.size(); i++) {
                    if (!ArrayList2.get(i).getTask().getTaskpublic()) {
                        tasks.add(ArrayList2.get(i).getTask());
                    }
                }
                taskAdapter = new TaiXeTaskAdapter(NhiemVuTaiXeFragment.this, R.layout.dong_nhiem_vu, tasks);
                lvTask.setAdapter(taskAdapter);
                taskAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<TaskDetail>> call, Throwable t) {
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

                dialog.setTitle("Add an Expense");
                dialog.setCancelable(true);
                Button btnDinhVi = dialog.findViewById(R.id.btnXemMap);
                btnDinhVi.setVisibility(View.GONE);
                Button btnChat = dialog.findViewById(R.id.btnChat);
                btnChat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(), ManHinhChat.class);
                        intent.putExtra("taskid", id);
                        intent.putExtra("role", "tx");
                        startActivity(intent);
                        dialog.dismiss();
                    }
                });

                Button btnComplete = dialog.findViewById(R.id.btnComplete);
                btnComplete.setVisibility(View.GONE);
                dialog.show();
            }

            @Override
            public void onFailure(Call<ArrayList<TaskDetail>> call, Throwable t) {

            }
        });

    }

}

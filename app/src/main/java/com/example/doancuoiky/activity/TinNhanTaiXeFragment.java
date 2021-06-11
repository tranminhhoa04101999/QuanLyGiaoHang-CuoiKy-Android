package com.example.doancuoiky.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.doancuoiky.API.APIService;
import com.example.doancuoiky.R;
import com.example.doancuoiky.adapter.TaiXeTaskAdapter;
import com.example.doancuoiky.model.Task;
import com.example.doancuoiky.model.TaskDetail;
import com.example.doancuoiky.model.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TinNhanTaiXeFragment extends Fragment {
    TextView tvTenTX;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle saveInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message_taixe, container, false);
        getDataName();
        tvTenTX = view.findViewById(R.id.tvTenTaiXe);
        return view;
    }

    private void getDataName() {
        Bundle bundle = this.getArguments();
        int idtaixe = 0;
        if (bundle != null) {
            idtaixe = bundle.getInt("id");
        }
        APIService.apiService.getUserById(idtaixe).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                tvTenTX.setText(response.body().getName());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
            }
        });
    }
}

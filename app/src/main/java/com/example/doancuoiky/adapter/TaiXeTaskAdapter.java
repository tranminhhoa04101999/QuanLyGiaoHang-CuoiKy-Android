package com.example.doancuoiky.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doancuoiky.R;
import com.example.doancuoiky.activity.ManHinhChinhNhiemVu;
import com.example.doancuoiky.activity.NhiemVuTaiXeFragment;
import com.example.doancuoiky.model.Task;

import java.util.List;

public class TaiXeTaskAdapter extends BaseAdapter {

    private NhiemVuTaiXeFragment context;
    private int layout;
    private List<Task> taskList;

    public TaiXeTaskAdapter(NhiemVuTaiXeFragment context, int layout, List<Task> taskList) {
        this.context = context;
        this.layout = layout;
        this.taskList = taskList;
    }

    @Override
    public int getCount() {
        return taskList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder {
        TextView tvClient, tvPickup;
        CheckBox cbApprove;
        ImageView ivDeleteTask, ivEditTask;
        View layout_dong_nv;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            holder.tvClient = convertView.findViewById(R.id.tvClient);
            holder.tvPickup = convertView.findViewById(R.id.tvPickup);
            holder.cbApprove = convertView.findViewById(R.id.cbApprove);
            holder.ivEditTask = convertView.findViewById(R.id.ivEditTask);
            holder.ivDeleteTask = convertView.findViewById(R.id.ivDeleteTask);
            holder.layout_dong_nv = convertView.findViewById(R.id.layout_dong_nv);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Task task = taskList.get(position);
        holder.tvClient.setText(task.getClient().getCompany());
        holder.tvPickup.setText(task.getPickup());
        holder.cbApprove.setChecked(task.getApprove());
        holder.ivEditTask.setVisibility(View.INVISIBLE);
        holder.ivDeleteTask.setVisibility(View.INVISIBLE);
        //bat su kien xoa sua

        holder.layout_dong_nv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (task.getApprove() && !task.getTaskpublic()) {
//                    Toast.makeText(context.getActivity(), "Approve", Toast.LENGTH_SHORT).show();
                    context.showDialog(task.getId());
                }
            }
        });
        return convertView;
    }
}

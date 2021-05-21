package com.example.doancuoiky.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doancuoiky.R;
import com.example.doancuoiky.activity.ManHinhNhiemVu;
import com.example.doancuoiky.model.Task;

import java.util.List;

public class TaskAdapter extends BaseAdapter {

    private ManHinhNhiemVu context;
    private int layout;
    private List<Task> taskList;

    public TaskAdapter(ManHinhNhiemVu context, int layout, List<Task> taskList) {
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
        TextView tvClient, tvPickup, tvDropoff;
        CheckBox cbApprove,cbPublic,cbCancel;
        ImageView ivDeleteTask, ivEditTask;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            holder.tvClient = convertView.findViewById(R.id.tvClient);
            holder.tvPickup =convertView.findViewById(R.id.tvPickup);
            holder.tvDropoff =convertView.findViewById(R.id.tvDropoff);
            holder.cbApprove=convertView.findViewById(R.id.cbApprove);
            holder.cbPublic=convertView.findViewById(R.id.cbPublic);
            holder.cbCancel=convertView.findViewById(R.id.cbCancel);
            holder.ivEditTask = convertView.findViewById(R.id.ivEditTask);
            holder.ivDeleteTask = convertView.findViewById(R.id.ivDeleteTask);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Task task = taskList.get(position);
        holder.tvClient.setText(task.getClient().getCompany());
        holder.tvPickup.setText(task.getPickup());
        holder.tvDropoff.setText(task.getDropoff());
        holder.cbApprove.setChecked(task.getApprove());
        holder.cbPublic.setChecked(task.getTaskpublic());
        holder.cbCancel.setChecked(task.getCancel());
        //bat su kien xoa sua
        holder.ivEditTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //    context.DialogSua(task.getId(),task.getTen(),task.getDiachi(),task.getPhone());
                Toast.makeText(context, "sua "+task.getId(), Toast.LENGTH_SHORT).show();
            }
        });
        holder.ivDeleteTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  context.DialogXoa(task.getId(),task.getTen());
                Toast.makeText(context, "xoa", Toast.LENGTH_SHORT).show();
            }
        });
        
        return convertView;
    }
}

package com.example.doancuoiky.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.doancuoiky.R;
import com.example.doancuoiky.activity.ManHinhChinhTaiKhoan;
import com.example.doancuoiky.model.Task;
import com.example.doancuoiky.model.User;

import java.util.List;

public class UserAdapter extends BaseAdapter {

    private ManHinhChinhTaiKhoan context;
    private int layout;
    private List<User> userList;

    public UserAdapter(ManHinhChinhTaiKhoan context, int layout, List<User> userList) {
        this.context = context;
        this.layout = layout;
        this.userList = userList;
    }

    @Override
    public int getCount() {
        return userList.size();
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
        TextView tvUsername,tvPassword,tvTenUser,tvDiaChi,tvRole;
        ImageView ivDeleteUser, ivEditUser;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            holder.tvUsername = convertView.findViewById(R.id.tvUsername);
            holder.tvPassword = convertView.findViewById(R.id.tvPassword);
            holder.tvTenUser = convertView.findViewById(R.id.tvTenUser);
            holder.tvDiaChi = convertView.findViewById(R.id.tvDiaChi);
            holder.tvRole = convertView.findViewById(R.id.tvRole);
            holder.ivEditUser = convertView.findViewById(R.id.ivEditUser);
            holder.ivDeleteUser = convertView.findViewById(R.id.ivDeleteUser);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        User user = userList.get(position);
        holder.tvUsername.setText(user.getUsername());
        holder.tvPassword.setText(user.getPassword());
        holder.tvTenUser.setText(user.getName());
        holder.tvDiaChi.setText(user.getAddress());
        holder.tvRole.setText(user.getRole().getRolename());
        //bat su kien xoa sua
        holder.ivEditUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               context.suaTaiKhoan(user);
               // Toast.makeText(context, "sua " + task.getId(), Toast.LENGTH_SHORT).show();
            }
        });
        holder.ivDeleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.DialogXoa(user);
            }
        });

        return convertView;
    }
}

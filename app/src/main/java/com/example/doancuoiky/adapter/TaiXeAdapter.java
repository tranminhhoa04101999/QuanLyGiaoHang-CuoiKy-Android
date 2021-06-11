package com.example.doancuoiky.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.doancuoiky.R;
import com.example.doancuoiky.activity.ManHinhChinhTaiKhoan;
import com.example.doancuoiky.activity.ManHinhThemPhanCong;
import com.example.doancuoiky.model.User;

import java.util.List;

public class TaiXeAdapter extends BaseAdapter {

    private ManHinhThemPhanCong context;
    private int layout;
    private List<User> userList;

    public TaiXeAdapter(ManHinhThemPhanCong context, int layout, List<User> userList) {
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
        TextView tvUsername,tvTenUser,tvDiaChi;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            holder.tvUsername = convertView.findViewById(R.id.tvUsername);
            holder.tvTenUser = convertView.findViewById(R.id.tvTenUser);
//            holder.tvDiaChi = convertView.findViewById(R.id.tvDiaChi);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        User user = userList.get(position);
        holder.tvUsername.setText(user.getUsername());
        holder.tvTenUser.setText(user.getName());
//        holder.tvDiaChi.setText(user.getAddress());

        return convertView;
    }
}

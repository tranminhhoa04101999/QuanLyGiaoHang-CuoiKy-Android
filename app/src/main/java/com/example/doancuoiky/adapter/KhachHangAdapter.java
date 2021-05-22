package com.example.doancuoiky.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doancuoiky.R;
import com.example.doancuoiky.activity.ManHinhChinhKhachHang;
import com.example.doancuoiky.model.Client;

import java.util.List;

public class KhachHangAdapter extends BaseAdapter {

    private ManHinhChinhKhachHang context;
    private int layout;
    private List<Client> clientList;

    public KhachHangAdapter(ManHinhChinhKhachHang context, int layout, List<Client> clientList) {
        this.context = context;
        this.layout = layout;
        this.clientList = clientList;
    }

    @Override
    public int getCount() {
        return clientList.size();
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
        TextView tvTen,tvID,tvDiaChi,tvPhone;
        ImageView ivDelete, ivEdit;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            holder.tvTen = convertView.findViewById(R.id.tvCompany);
            holder.ivEdit = convertView.findViewById(R.id.ivEditClient);
            holder.ivDelete = convertView.findViewById(R.id.ivDeleteClient);
           // holder.tvID=convertView.findViewById(R.id.tvCompany);

            holder.tvPhone=convertView.findViewById(R.id.tvSDT);
            holder.tvDiaChi=convertView.findViewById(R.id.tvAddress);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Client khachHang = clientList.get(position);
        holder.tvTen.setText(khachHang.getCompany());
       // holder.tvID.setText("Công ty: "+ khachHang.getCompany());

        holder.tvPhone.setText(khachHang.getPhone());
        holder.tvDiaChi.setText(khachHang.getAddress());

        //bat su kien xoa sua
        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.DialogSua(khachHang.getClientid(),khachHang.getCompany(),khachHang.getAddress(),khachHang.getPhone());
                Toast.makeText(context, "sua " + khachHang.getCompany(), Toast.LENGTH_SHORT).show();
            }
        });

        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.DialogXoa(khachHang.getClientid());
            }
        });

        return convertView;
    }
}

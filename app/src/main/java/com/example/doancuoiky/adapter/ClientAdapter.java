package com.example.doancuoiky.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.doancuoiky.R;
import com.example.doancuoiky.activity.ManHinhChinhKhachHang;
import com.example.doancuoiky.model.Client;

import java.util.List;

public class ClientAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<Client> clientList;

    public ClientAdapter(Context context, int layout, List<Client> clientList) {
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
        TextView tvCty;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
          //  holder.tvCty = convertView.findViewById(R.id.tvClientspn);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Client client = clientList.get(position);
        holder.tvCty.setText(client.getCompany());


        return convertView;
    }
}

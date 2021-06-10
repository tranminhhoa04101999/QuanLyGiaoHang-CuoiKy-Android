package com.example.doancuoiky.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.doancuoiky.R;
import com.example.doancuoiky.model.Role;

import java.util.List;

public class RoleSpinnerAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<Role> roleList;

    public RoleSpinnerAdapter(Context context, int layout, List<Role> roleList) {
        this.context = context;
        this.layout = layout;
        this.roleList = roleList;
    }

    @Override
    public int getCount() {
        return roleList.size();
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
        TextView tvRole;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            holder.tvRole = convertView.findViewById(R.id.tv_role_spn);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Role role = roleList.get(position);
        holder.tvRole.setText(role.getRolename());


        return convertView;
    }
}

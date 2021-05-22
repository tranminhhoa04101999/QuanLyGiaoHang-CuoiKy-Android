package com.example.doancuoiky.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.doancuoiky.R;
import com.example.doancuoiky.activity.ThongKe;
import com.example.doancuoiky.model.ThongKeResult;

import java.util.List;

public class ThongKeAdapter extends BaseAdapter {
    ThongKe context;
    private int layout;
    private List<ThongKeResult> thongKeResults;
    //private List<Integer> soLuongList;


    public ThongKeAdapter(ThongKe context, int layout, List<ThongKeResult> thongKeResults) {
        this.context = context;
        this.layout = layout;
        this.thongKeResults = thongKeResults;
    }

    public List<ThongKeResult> getThongKeResults() {
        return thongKeResults;
    }

    @Override
    public int getCount() {
        return thongKeResults.size();
    }

    @Override
    public Object getItem(int position) {
        return thongKeResults.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(layout,null);

        // ánh xạ và gán giá trị
        TextView txtTenSP =  (TextView) convertView.findViewById(R.id.tv_stt);
        txtTenSP.setText("Stt : " + position);

        TextView txtXuatXu = convertView.findViewById(R.id.tv_loaiTask);
        txtXuatXu.setText("Loại task : "+ thongKeResults.get(position).getLoaiTask());

        TextView txtDonGia = convertView.findViewById(R.id.tv_soLuong);
        txtDonGia.setText("Số lượng đơn : " + String.valueOf(thongKeResults.get(position).getSoLuong()));


        //ImageView imgSP = (ImageView) convertView.findViewById(R.id.hienthihinhsanphamtop);
        //Bitmap bitmap = BitmapFactory.decodeByteArray(sanPhamList.get(position).getHinhAnh(),0,sanPhamList.get(position).getHinhAnh().length);
        //imgSP.setImageBitmap(bitmap);

        return convertView;
    }

}

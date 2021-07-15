package com.example.doancuoiky.activity;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doancuoiky.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;


public class BieuDo extends AppCompatActivity {
    ArrayList<Integer> soLuongList = new ArrayList<>();
    ArrayList<String> loaiTask = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart);
        Bundle bundle = getIntent().getExtras();
        soLuongList = bundle.getIntegerArrayList("soluong");
        loaiTask = bundle.getStringArrayList("loaiTask");
        PieChart pieChart = findViewById(R.id.pieChart);

        ArrayList<PieEntry> visitors = new ArrayList<>();
        int sum = 0;
        for (int i : soLuongList) {
            sum += i;
        }
        for (int i = 0; i < soLuongList.size(); i++) {
            if (soLuongList.get(i) < 1) continue;
            visitors.add(new PieEntry(soLuongList.get(i) * 100 / sum, loaiTask.get(i)));
        }

        PieDataSet pieDataSet = new PieDataSet(visitors, "Thống kê");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(16f);

        PieData pieData = new PieData(pieDataSet);

        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);
        //pieChart.setCenterText("Thống kê");
        pieChart.animate();
    }
}
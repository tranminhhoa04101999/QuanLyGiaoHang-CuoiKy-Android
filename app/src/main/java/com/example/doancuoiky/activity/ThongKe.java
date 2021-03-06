package com.example.doancuoiky.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doancuoiky.API.APIService;
import com.example.doancuoiky.R;
import com.example.doancuoiky.adapter.ThongKeAdapter;
import com.example.doancuoiky.model.Task;
import com.example.doancuoiky.model.ThongKeResult;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThongKe extends AppCompatActivity {
    ListView listViewthongKe;
    ArrayList<ThongKeResult> thongKeResults = new ArrayList<>();
    ThongKeAdapter thongKeAdapter;
    ArrayList<Task> tasks;
    TextView tv_tong;

    Handler handler = new Handler();
    Runnable runnable;
    int delay = 500;


    @Override
    protected void onResume() {
        handler.postDelayed( runnable = new Runnable() {
            public void run() {
                //do something
                getTask();
                handler.postDelayed(runnable, delay);
            }
        }, delay);
        super.onResume();
    }


    @Override
    protected void onPause() {
        handler.removeCallbacks(runnable);
        super.onPause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thongke);

        listViewthongKe = findViewById(R.id.lv_thongKe);
        tv_tong = findViewById(R.id.txt_task);
        getTask();
        //hienthithongke(tasks);
    }

    @Override
    public boolean onCreatePanelMenu(int featureId, Menu menu) {
        getMenuInflater().inflate(R.menu.menu_thongke, menu);
        return super.onCreatePanelMenu(featureId, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_option0:
                Intent intentTK = new Intent(this, BieuDo.class);
                Bundle bundle = new Bundle();
                ArrayList<String> loaiTask = new ArrayList<>();
                for (int i = 0; i < thongKeResults.size(); i++) {
                    loaiTask.add(thongKeResults.get(i).getLoaiTask());
                }
                ArrayList<Integer> soLuong = new ArrayList<>();
                for (int i = 0; i < thongKeResults.size(); i++) {
                    soLuong.add(thongKeResults.get(i).getSoLuong());
                }
                bundle.putStringArrayList("loaiTask", loaiTask);
                bundle.putIntegerArrayList("soluong", soLuong);
                intentTK.putExtras(bundle);
                startActivity(intentTK);
                break;

            case R.id.menu_option1:
                hienthithongke(tasks);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        hienthithongke(tasks);
    }

    public void hienthithongke(ArrayList<Task> tasks) {
        //databaseHelper = new DatabaseHelper(ThongKeTask.this,"QLDHDB.sqlite",null,1);
        //databaseHelper.QueryData("DELETE FROM SANPHAM WHERE MASP = '3'");
        //String sql = "SELECT * FROM SANPHAM";
        //Cursor cursor = databaseHelper.GetData(sql);
        thongKeResults.clear();
        //while(cursor.moveToNext()){
        int danggiao = 0, chuagiao = 0, hoanthanh = 0;
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getApprove() && !tasks.get(i).getTaskpublic())
                danggiao++;
            else if (!tasks.get(i).getApprove())
                chuagiao++;
            if (tasks.get(i).getTaskpublic()) hoanthanh++;
        }
        thongKeResults.add(new ThongKeResult("??ang giao", danggiao));
        thongKeResults.add(new ThongKeResult("Ch??a giao", chuagiao));
        thongKeResults.add(new ThongKeResult("Ho??n th??nh", hoanthanh));
//            Log.e("SELECT * FROM :",MASP + "  "+ tensp + "  "+xuatxu+"  "+dongia+"  "+hinhanh);
        thongKeAdapter = new ThongKeAdapter(this, R.layout.tung_thongke, thongKeResults);
        listViewthongKe.setAdapter(thongKeAdapter);
        tv_tong.setText(String.valueOf(tasks.size()));
    }

    public void getTask() {
        APIService.apiService.getTasks().enqueue(new Callback<ArrayList<Task>>() {
            @Override
            public void onResponse(Call<ArrayList<Task>> call, Response<ArrayList<Task>> response) {
                tasks = response.body();
                hienthithongke(tasks);
            }

            @Override
            public void onFailure(Call<ArrayList<Task>> call, Throwable t) {

            }
        });
    }

    /*public byte[] ConverttoArrayByte(ImageView img){
        BitmapDrawable bitmapDrawable = (BitmapDrawable) img.getDrawable();
        Bitmap bitmap =bitmapDrawable.getBitmap();

        ByteArrayOutputStream stream =new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        return stream.toByteArray();
    }*/
}

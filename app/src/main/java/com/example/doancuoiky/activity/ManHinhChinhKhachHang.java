package com.example.doancuoiky.activity;

        import android.app.AlertDialog;
        import android.app.Dialog;
        import android.content.DialogInterface;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.view.Window;
        import android.widget.Button;
        import android.widget.CheckBox;
        import android.widget.EditText;
        import android.widget.ListView;
        import android.widget.Spinner;
        import android.widget.Toast;

        import androidx.annotation.NonNull;
        import androidx.appcompat.app.AppCompatActivity;

        import com.example.doancuoiky.API.APIService;
        import com.example.doancuoiky.R;
        import com.example.doancuoiky.adapter.ClientAdapter;
        import com.example.doancuoiky.adapter.KhachHangAdapter;
        import com.example.doancuoiky.adapter.TaskAdapter;
        import com.example.doancuoiky.model.Client;
        import com.example.doancuoiky.model.Task;

        import java.util.ArrayList;

        import retrofit2.Call;
        import retrofit2.Callback;
        import retrofit2.Response;

public class ManHinhChinhKhachHang extends AppCompatActivity {
    ListView lvClient;
    Spinner spnKH;
    //    ArrayList<Task> taskArrayList;
   // ClientAdapter taskAdapter;
    KhachHangAdapter khachHangAdapter;
    ArrayList<Client> clients;
    Client client=new Client();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_chinh_khach_hang);
        lvClient = findViewById(R.id.lvClient);
//        taskArrayList = new ArrayList<>();
//        taskAdapter = new TaskAdapter(this, R.layout.dong_nhiem_vu, taskArrayList);
//        lvTask.setAdapter(taskAdapter);
        getDataClient();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.them_khach_hang, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.themKhachHang) {
            DialogThem();
        }
        return super.onOptionsItemSelected(item);
    }


    private void DialogThem() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_them_khach_hang);

        EditText etTenAdd = dialog.findViewById(R.id.etThemTenKhachHang);
        EditText etSDTAdd = dialog.findViewById(R.id.etThemSDTKhachHang);
        EditText etDiachiAdd = dialog.findViewById(R.id.etThemDiachiKhachHang);
        Button btnThem = dialog.findViewById(R.id.btnThem);
        Button btnHuy = dialog.findViewById(R.id.btnHuy);

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setCanceledOnTouchOutside(false);

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenKhachHang = etTenAdd.getText().toString();
                String SDTKhachHang = etSDTAdd.getText().toString();
                String DiachiKhachHang = etDiachiAdd.getText().toString();
                if (tenKhachHang.equals("")) {
                    Toast.makeText(ManHinhChinhKhachHang.this, "Vui lòng nhập tên!", Toast.LENGTH_SHORT).show();
                }else if (SDTKhachHang.equals("")){
                    Toast.makeText(ManHinhChinhKhachHang.this, "Vui lòng nhập SDT!", Toast.LENGTH_SHORT).show();

                }else if (DiachiKhachHang.equals("")){
                    Toast.makeText(ManHinhChinhKhachHang.this, "Vui lòng nhập địa chỉ!", Toast.LENGTH_SHORT).show();

                }
                else {


                     //Client client = clients.get(spnKH.getSelectedItemPosition());
                    client.setClientid(0);
                    client.setCompany(tenKhachHang);
                    client.setPhone(SDTKhachHang);
                    client.setAddress(DiachiKhachHang);

                    addClient(client);
//                    Toast.makeText(ManHinhNhiemVu.this, "Thêm thành công!" + client.toString(), Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    getDataClient();
                }
            }
        });
        dialog.show();
    }


    private void addClient(Client client) {
        APIService.apiService.addClient(client).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ManHinhChinhKhachHang.this, "Thêm Mới Thành Công", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("ececeeee", t.getMessage());
            }
        });
    }

    private void deleteClient(int id) {
        APIService.apiService.deleteClient(id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ManHinhChinhKhachHang.this, "Xoá Thành Công", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("ececeeee", t.getMessage());
            }
        });
    }


    private void getDataClient() {
        APIService.apiService.getClients().enqueue(new Callback<ArrayList<Client>>() {
            @Override
            public void onResponse(Call<ArrayList<Client>> call, Response<ArrayList<Client>> response) {
                ArrayList<Client> clientArrayList = response.body();

                khachHangAdapter = new KhachHangAdapter(ManHinhChinhKhachHang.this, R.layout.dong_khach_hang, clientArrayList);
               // spnKH.setAdapter(clientAdapter);
                lvClient.setAdapter(khachHangAdapter);
                khachHangAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<Client>> call, Throwable t) {
                Toast.makeText(ManHinhChinhKhachHang.this, "Call API that bai", Toast.LENGTH_SHORT).show();
            }
        });
    }
//    private void DialogThem() {
//        Dialog dialog = new Dialog(this);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.dialog_them_nhiem_vu);
//
//        spnKH = dialog.findViewById(R.id.spnKH);
//        getDataClient();
//
//        EditText etPickup = dialog.findViewById(R.id.etPickup);
//
//        EditText etDropoff = dialog.findViewById(R.id.etDropoff);
//
//        CheckBox cbApprove = dialog.findViewById(R.id.cbApprovee);
//        CheckBox cbPublic = dialog.findViewById(R.id.cbPublice);
//        CheckBox cbCancel = dialog.findViewById(R.id.cbCancele);
//
//        Button btnThem = dialog.findViewById(R.id.btnThem);
//        Button btnHuy = dialog.findViewById(R.id.btnHuy);
//
//
//        btnHuy.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//        dialog.setCanceledOnTouchOutside(false);
//
//        btnThem.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String pickupdate = etPickup.getText().toString();
//                String dropoffdate = etDropoff.getText().toString();
//                if (pickupdate.equals("")) {
//                    Toast.makeText(ManHinhChinhKhachHang.this, "Vui lòng nhập ngày!", Toast.LENGTH_SHORT).show();
//                    etPickup.requestFocus();
//                } else if (dropoffdate.equals("")) {
//                    Toast.makeText(ManHinhChinhKhachHang.this, "Vui lòng nhập ngày!", Toast.LENGTH_SHORT).show();
//                    etDropoff.requestFocus();
//
//                } else {
//                    Client client = clients.get(spnKH.getSelectedItemPosition());
//                    task.setId(0);
//                    task.setClient(null);
//                    task.setPickup(pickupdate);
//                    task.setDropoff(dropoffdate);
//                    task.setApprove(cbApprove.isChecked());
//                    task.setTaskpublic(cbPublic.isChecked());
//                    task.setCancel(cbCancel.isChecked());
//
//                    addTask(task, client.getClientid());
////                    Toast.makeText(ManHinhNhiemVu.this, "Thêm thành công!" + client.toString(), Toast.LENGTH_SHORT).show();
//                    dialog.dismiss();
//                    getDataTask();
//                }
//            }
//        });
//        dialog.show();
//    }

//    private void addTask(Task task, int idClient) {
//        APIService.apiService.addTask(task, idClient).enqueue(new Callback<Void>() {
//            @Override
//            public void onResponse(Call<Void> call, Response<Void> response) {
//                if (response.isSuccessful()) {
//                    Toast.makeText(ManHinhChinhKhachHang.this, "Thêm Mới Thành Công", Toast.LENGTH_SHORT).show();
//
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<Void> call, Throwable t) {
//                Log.d("ececeeee", t.getMessage());
//            }
//        });
//    }
//
//    private void deleteTask(int id) {
//        APIService.apiService.deleteTask(id).enqueue(new Callback<Void>() {
//            @Override
//            public void onResponse(Call<Void> call, Response<Void> response) {
//                if (response.isSuccessful()) {
//                    Toast.makeText(ManHinhChinhKhachHang.this, "Xoá Thành Công", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Void> call, Throwable t) {
//                Log.d("ececeeee", t.getMessage());
//            }
//        });
//    }


    public void DialogSua(int id, String ten,String diachi,String SDT) {
        Dialog dialog = new Dialog(this);
        dialog.setCanceledOnTouchOutside(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_sua_khach_hang);

        EditText etTenKhachHang = dialog.findViewById(R.id.etSuaTenKhachHang);
        EditText etSDTKhachHang = dialog.findViewById(R.id.etSuaSDTKhachHang);
        EditText etDiaChiKhachHang = dialog.findViewById(R.id.etSuaDiachiKhachHang);
        Button btnSua = dialog.findViewById(R.id.btnSua);
        Button btnHuy = dialog.findViewById(R.id.btnHuys);
        etTenKhachHang.setText(ten);
        etSDTKhachHang.setText(SDT);
        etDiaChiKhachHang.setText(diachi);

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenMoi = etTenKhachHang.getText().toString();
                String SDTMoi = etSDTKhachHang.getText().toString();
                String diaChiMoi=etDiaChiKhachHang.getText().toString();
                if (tenMoi.equals("")) {
                    Toast.makeText(ManHinhChinhKhachHang.this, "Vui lòng nhập tên!", Toast.LENGTH_SHORT).show();
                }
                else if (SDTMoi.equals("")){
                    Toast.makeText(ManHinhChinhKhachHang.this, "Vui lòng nhập SDT!", Toast.LENGTH_SHORT).show();
                }else if (diaChiMoi.equals("")){
                    Toast.makeText(ManHinhChinhKhachHang.this, "Vui lòng nhập địa chỉ!", Toast.LENGTH_SHORT).show();
                }
                else {
//                    String query=String.format("UPDATE KHACHHANG SET TENKH='%s',DIACHI='%s',DIENTHOAI='%s' WHERE MAKH=%d",tenMoi,diaChiMoi,SDTMoi,id);
//                    database.QueryData(query);
//                    Toast.makeText(com.an.qldhsp.QLKhachHang.this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
//                    dialog.dismiss();
//                    getDataKhachHang();
                }
            }
        });
        dialog.show();
    }


    public void DialogXoa(int id) {
        AlertDialog.Builder dialogXoa = new AlertDialog.Builder(this);
        dialogXoa.setMessage("Bạn có muốn xóa client: '" + client.getCompany() + "' ?");
        dialogXoa.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteClient(id);
//                Toast.makeText(ManHinhNhiemVu.this, "Xóa thành công!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                getDataClient();
            }
        });
        dialogXoa.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialogXoa.show();
   }


}
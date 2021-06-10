package com.example.doancuoiky.model;

public class ThongKeResult {

    private String loaiTask;
    private int soLuong;

    public ThongKeResult(String loaiTask, int soLuong) {
        this.loaiTask = loaiTask;
        this.soLuong = soLuong;
    }

    public String getLoaiTask() {
        return loaiTask;
    }

    public void setLoaiTask(String loaiTask) {
        this.loaiTask = loaiTask;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    @Override
    public String toString() {
        return "hello";
    }
}

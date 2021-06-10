package com.example.doancuoiky.model;

public class TaskDetail{
    private int id;
    private int driverid;
    private int taskid;


    public TaskDetail() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDriverid() {
        return driverid;
    }

    public void setDriverid(int driverid) {
        this.driverid = driverid;
    }

    public int getTaskid() {
        return taskid;
    }

    public void setTaskid(int taskid) {
        this.taskid = taskid;
    }

    public TaskDetail(int id, int driverid, int taskid) {
        this.id = id;
        this.driverid = driverid;
        this.taskid = taskid;
    }
}
package com.example.doancuoiky.model;

public class Task {
    private int id;
    private String pickup;
    private String dropoff;
    private Boolean approve;
    private Boolean taskpublic;
    private Boolean cancel;
    private Client client;

    public Task(int id, String pickup, String dropoff, Boolean approve, Boolean taskpublic, Boolean cancel, Client client) {
        this.id = id;
        this.pickup = pickup;
        this.dropoff = dropoff;
        this.approve = approve;
        this.taskpublic = taskpublic;
        this.cancel = cancel;
        this.client = client;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPickup() {
        return pickup;
    }

    public void setPickup(String pickup) {
        this.pickup = pickup;
    }

    public String getDropoff() {
        return dropoff;
    }

    public void setDropoff(String dropoff) {
        this.dropoff = dropoff;
    }

    public Boolean getApprove() {
        return approve;
    }

    public void setApprove(Boolean approve) {
        this.approve = approve;
    }

    public Boolean getTaskpublic() {
        return taskpublic;
    }

    public void setTaskpublic(Boolean taskpublic) {
        this.taskpublic = taskpublic;
    }

    public Boolean getCancel() {
        return cancel;
    }

    public void setCancel(Boolean cancel) {
        this.cancel = cancel;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", pickup='" + pickup + '\'' +
                ", dropoff='" + dropoff + '\'' +
                ", approve=" + approve +
                ", taskpublic=" + taskpublic +
                ", cancel=" + cancel +
                ", client=" + client +
                '}';
    }
}
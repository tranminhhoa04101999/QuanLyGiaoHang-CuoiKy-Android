package com.example.doancuoiky.model;

public class TaskDetail{
    private int id;
    private User driver;
    private Task task;
    private String task_note;
    private String chat;

    public TaskDetail() {
    }

    public TaskDetail(int id, User driver, Task task, String task_note, String chat) {
        this.id = id;
        this.driver = driver;
        this.task = task;
        this.task_note = task_note;
        this.chat = chat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getDriver() {
        return driver;
    }

    public void setDriver(User driver) {
        this.driver = driver;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public String getTask_note() {
        return task_note;
    }

    public void setTask_note(String task_note) {
        this.task_note = task_note;
    }

    public String getChat() {
        return chat;
    }

    public void setChat(String chat) {
        this.chat = chat;
    }

    @Override
    public String toString() {
        return "TaskDetail{" +
                "id=" + id +
                ", driver=" + driver +
                ", task=" + task +
                ", tasknote='" + task_note + '\'' +
                ", chat='" + chat + '\'' +
                '}';
    }
}
package com.example.doancuoiky.model;

import java.io.Serializable;

public class TaskDetail implements Serializable {
    private int id;
    private User user;
    private Task task;
    private String task_note;
    private String chat;

    public TaskDetail() {
    }

    public TaskDetail(int id, User user, Task task, String task_note, String chat) {
        this.id = id;
        this.user = user;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
                ", user=" + user +
                ", task=" + task +
                ", task_note='" + task_note + '\'' +
                ", chat='" + chat + '\'' +
                '}';
    }
}
package com.example.doancuoiky.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Role implements Serializable {

    @SerializedName("id")
    private int id;

    @SerializedName("rolename")
    private String rolename;

    public Role(int id, String rolename) {
        this.id = id;
        this.rolename = rolename;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", rolename='" + rolename + '\'' +
                '}';
    }
}

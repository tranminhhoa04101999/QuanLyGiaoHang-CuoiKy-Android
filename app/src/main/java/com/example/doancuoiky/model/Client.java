package com.example.doancuoiky.model;

import java.io.Serializable;

public class Client implements Serializable {
    private int clientid;
    private String company;
    private String phone;
    private String address;
    private String username;

    public Client(int clientid, String company, String phone, String address, String username) {
        this.clientid = clientid;
        this.company = company;
        this.phone = phone;
        this.address = address;
        this.username = username;
    }

    public Client() {
    }

    public int getClientid() {
        return clientid;
    }

    public void setClientid(int clientid) {
        this.clientid = clientid;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "Client{" +
                "clientid=" + clientid +
                ", company='" + company + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
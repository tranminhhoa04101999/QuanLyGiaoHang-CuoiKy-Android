package com.example.doancuoiky.model;

import java.io.Serializable;

public class Client implements Serializable {
    private int clientid;
    private String company;
    private String phone;
    private String address;

    public Client(int clientid, String company, String phone, String address) {
        this.clientid = clientid;
        this.company = company;
        this.phone = phone;
        this.address = address;
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

    @Override
    public String toString() {
        return "Client{" +
                "clientid=" + clientid +
                ", company='" + company + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}

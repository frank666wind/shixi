package com.shixi.demo.order.entity;

public class Order{
    private Integer orderid;
    private Integer complete;
    private Float longitude;
    private Float latitude;
    private String username;
    private String phone;
    private String receiver;
    private String address;

    public Order() {

    }

    public Order(Integer orderid, Integer complete, String username, String phone, String receiver, String address) {
        this.orderid = orderid;
        this.complete = complete;
        this.username = username;
        this.phone = phone;
        this.receiver = receiver;
        this.address = address;
    }

    public Integer getOrderid() {
        return orderid;
    }

    public void setOrderid(Integer orderid) {
        this.orderid = orderid;
    }

    public Integer getComplete() {
        return complete;
    }

    public void setComplete(Integer complete) {
        this.complete = complete;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderid=" + orderid +
                ", complete=" + complete +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", username='" + username + '\'' +
                ", phone='" + phone + '\'' +
                ", receiver='" + receiver + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
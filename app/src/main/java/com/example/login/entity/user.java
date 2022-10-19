package com.entity;

public class user {
    private int id;
    private String username;//用户名
    private String passwards;//密码

    public user() {
        super();
    }

    public user(int id, String username, String passwards) {
        super();
        this.id = id;
        this.username = username;
        this.passwards = passwards;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPasswards() {
        return passwards;
    }
    public void setPasswars(String passwards) {
        this.passwards = passwards;
    }

    //重写的toString()方法，便于测试Users类
    @Override
    public String toString() {
        System.out.println("id:"+this.getId()+", username:"+this.getUsername()+",password:"+this.getPasswards());
        return super.toString();
    }
}

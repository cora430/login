package com.test;

import com.dao.UserDao;
import com.entity.user;

import java.util.List;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
      //测试Users类
        //int userid=3;
        //String username="gg",password="543wwss"
        //user user = new user(userid, username, password);
        //user.toString();
      //由于Users类中重写了toString方法，则会在控制台输出 实例化的user对象的信息

        //测试BaseDao类
//        BaseDao baseDao = new BaseDao();
//        Connection connection = baseDao.getConnection();
//        baseDao.closeConnection(connection);
        //测试UsersDao
        UserDao usersDao = new UserDao();
        List<user> users = usersDao.findUsers();
        //循环输出所有数据库中的值
        for (user user : users) {
            System.out.println("id:"+user.getId()+",username:"+user.getUsername()+",password:"+user.getPasswards());
        }

        //测试唯一查询
        int id;
        Scanner sc=new Scanner(System.in);
        System.out.println("请输入要查询的id：");
        id=sc.nextInt();
        System.out.println("\n");
        user u = usersDao.selectById(id);
        System.out.println("查询信息为==id:"+u.getId()+" ,username:"+u.getUsername()+" ,password:"+u.getPasswards()+"\n");

        int count; //count用来接收返回值，若为0，则说明插入/删除不成功
        //测试插入
        int userid;
        String username,password;
        System.out.println("请输入要插入的id、用户名、密码：");
        userid=sc.nextInt();
        username=sc.next();
        password=sc.next();
        System.out.println("\n");
        user user = new user(userid, username, password);//初始化一个Users对象
        count = usersDao.insert(user);
        System.out.println("  插入count="+count+"\n");

        //测试删除
        int del;
        System.out.println("请输入要删除的id：");
        del=sc.nextInt();
        System.out.println("\n");
        count = usersDao.delete(del);
        System.out.println("  删除count="+count+"\n");

        //测试更新
        user us = new user(5, "lol", "daw2we");
        count = usersDao.update(us);
        System.out.println("  更新count="+count+"\n");
    }
}
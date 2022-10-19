package com.example.login.dao;

import com.example.login.entity.user;
import com.example.login.utils.BaseDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    BaseDao db = new BaseDao();//初始化工具类
    String sql = "select * from user";//封装sql语句，这边的‘user’和数据库中的表同名
    String command = "use login";//选择database(必做，不然会报错)

    public List<user> findUsers() {         //Lisr<Users> 一个list（相当于一个大数组）
        List<user> users = new ArrayList<user>();//初始化list
        //1、连接到数据库
        Connection connection = db.getConnection();

        try {
            //2、执行SQL查询
            PreparedStatement pre = connection.prepareStatement(command);
            pre.execute();
            PreparedStatement pst = connection.prepareStatement(sql);
            //3、把查询结果放到结果集
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                user user = new user(rs.getInt(1), rs.getString(2), rs.getString(3));
                users.add(user);
            }//循环将结果保存到list中。 使用rs.get***()获取到结果集里面的相应类型的每一列的值，然后通过构造方法赋值给user，进而通过add()保存到list
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //4、断开数据库连接
            db.closeConnection(connection);//放到finally中，无论是否抛出异常，最后都会执行该语句
        }
        return users;
    }
    /**
     * 唯一查询
     * @param id 通过id唯一查询数据库中的数据 [注：唯一查询的时候是
     * 根据id查询的，所以要把数据库中的表的id字段设置为primary key]
     * @return Users的一个实例化对象,如果查找到了就返回的是数据库中
     * 的一条记录
     * 否则就是 null;
     */
    public user selectById(int id) {
        user user = new user();
        //1.连接到数据库
        Connection connection = db.getConnection();
        //2.拼接sql语句
        String  sql = "select * from user where id="+id;
        try {
            System.out.println("准备查询....");

            PreparedStatement pre = connection.prepareStatement(command);
            pre.execute();

            //3.执行发送到数据库的sql语句
            Statement st = connection.createStatement();//创建一个Statement对象，用于向数据库发送数据
            ResultSet rs = st.executeQuery(sql);//执行给定的sql语句,返回结果集对象
            while (rs.next()) {
                user = new user(rs.getInt(1), rs.getString(2), rs.getString(3));
                //执行SQL语句查找到了数据，就把数据和实体类的一个对象映射起来。
            }

            System.out.println("已查询！！！！");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //4.关闭连接，释放资源
            db.closeConnection(connection);
        }
        return user;
    }

    /**
     * 删除
     * @param id
     * @return 删除的记录数--如果删除一条记录就返回1，若是没有删除成功则返回0
     */
    public int delete(int id) {
        int count = 0;
        //1.连接到数据库
        Connection connection = db.getConnection();
        //2.拼接sql语句
        String sql = "delete from user where id="+id;
        try {
            System.out.println("准备删除id为："+id+" 的记录...");

            PreparedStatement pre = connection.prepareStatement(command);
            pre.execute();

            //3.执行发送到数据库的sql语句
            Statement st = connection.createStatement();
            count = st.executeUpdate(sql);//count接收方法的返回值，为0 表示删除失败

            if (count != 0) {
                System.out.print("删除成功！！！ ");
            } else {
                System.out.print("delete error!!! ");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //4.关闭数据库连接，释放资源
            db.closeConnection(connection);
        }
        return count;
    }

    /**
     * 插入
     * @param user Users类的对象，插入数据库的是该条记录
     * @return 插入到数据库的记录数--如果插入了一条记录就返回1，若是没有插入成功则返回0
     */
    public int insert(user user) {
        int count = 0;
        Connection connection = db.getConnection();
        String sql = "insert into user(id, username, password) values("+user.getId()+", '"+user.getUsername()+"', '"+user.getPasswards()+"')";

        try {
            System.out.println("准备插入记录==="+user.getId()+":"+user.getUsername()+":"+user.getPasswards()+"...");

            PreparedStatement pre = connection.prepareStatement(command);
            pre.execute();

            Statement st = connection.createStatement();
            count = st.executeUpdate(sql);

            if (count != 0) {
                System.out.print("插入成功！！！ ");
            } else {
                System.out.print("insert error!!! ");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.closeConnection(connection);
        }
        return count;
    }

    /**
     * 更新
     * @param user Users类的对象，从数据库更新的是该条记录
     * @return 从数据库更新的记录数--如果更新了一条记录就返回1，若是没有更新成功则返回0
     */
    public int update(user user) {
        int count = 0;
        Connection connection = db.getConnection();
        String sql = "update user set username='"+user.getUsername()+"' where id="+user.getId();

        try {
            System.out.println("准备更新 id 为 "+user.getId()+"的记录...");

            PreparedStatement pre = connection.prepareStatement(command);
            pre.execute();

            Statement st = connection.createStatement();
            count = st.executeUpdate(sql);

            if (count != 0) {
                System.out.print("更新成功！！！ ");
            } else {
                System.out.print("update error!!! ");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.closeConnection(connection);
        }
        return count;
    }

}

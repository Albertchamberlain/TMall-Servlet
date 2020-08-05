package com.amos.dao.impl;

import com.amos.bean.User;
import com.amos.dao.UserDao;
import com.amos.utils.DBUtil;
import com.amos.utils.DateUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Amos
 * @date 8/5/2020 8:47 PM
 */
public class UserDaoImpl  implements UserDao {
    @Override
    public int getTotal() {
        int total = 0;
        try (Connection connection = DBUtil.getConnection();
             Statement statement = connection.createStatement()) {
            String sql = "select count(*) from user where deleteAt is null";
            ResultSet rs = statement.executeQuery(sql);
            if(rs.next()){
                total = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }

    @Override
    public void add(User user) {
        String sql = "insert into user (`name`,`password`) values (?,?)";
        try(Connection c = DBUtil.getConnection();
            PreparedStatement ps = c.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)){
            ps.setString(1,user.getName());
            ps.setString(2, user.getPassword());
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()){
                int id = rs.getInt(1);
                user.setId(id);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    public void update(User user) {
        String sql = "update user set name = ? , password = ? where deleteAt is null and id = ?";
        try(Connection c = DBUtil.getConnection();
            PreparedStatement ps = c.prepareStatement(sql)){
            ps.setString(1,user.getName());
            ps.setString(2,user.getPassword());
            ps.setInt(3,user.getId());
            ps.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    public void delete(Integer id) {
        String sql = "update user set deleteAt = ? where deleteAt is null and id = ?";
        try(Connection c = DBUtil.getConnection();
            PreparedStatement ps = c.prepareStatement(sql)){
            ps.setTimestamp(1, DateUtil.nowTimestamp());
            ps.setInt(2,id);
            ps.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<User> list(Integer start, Integer count) {
        List<User> beans = new ArrayList<>();
        String sql = "select * from user where deleteAt is null limit ?,?";
        try(Connection c = DBUtil.getConnection();
            PreparedStatement ps = c.prepareStatement(sql)){
            ps.setInt(1,start);
            ps.setInt(2,count);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                User bean = new User();
                bean.setId(rs.getInt("id"));
                bean.setName(rs.getString("name"));
                bean.setGroup(rs.getString("group_"));
                bean.setPassword(rs.getString("password"));
                beans.add(bean);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return beans;
    }

    @Override
    public User get(Integer id) {
        User user = null;
        String sql = "select * from user where deleteAt is null and id=?";
        try(Connection c = DBUtil.getConnection();
            PreparedStatement ps = c.prepareStatement(sql)){
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setGroup(rs.getString("group_"));
                user.setPassword(rs.getString("password"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public User get(String name) {
        User user = null;
        String sql = "select * from user where deleteAt is null and name=?";
        try(Connection c = DBUtil.getConnection();
            PreparedStatement ps = c.prepareStatement(sql)){
            ps.setString(1,name);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                user = new User();
                user.setId(rs.getInt("id"));
                user.setGroup(rs.getString("group_"));
                user.setName(rs.getString("name"));
                user.setPassword(rs.getString("password"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public User get(String name, String password) {
        User user = null;
        String sql = "select * from user where deleteAt is null and name=? and password=?";
        try(Connection c = DBUtil.getConnection();
            PreparedStatement ps = c.prepareStatement(sql)){
            ps.setString(1,name);
            ps.setString(2,password);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setPassword(rs.getString("password"));
                user.setGroup(rs.getString("group_"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return user;
    }
}

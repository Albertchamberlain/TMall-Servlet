package com.amos.dao.impl;

import com.amos.bean.Order;
import com.amos.dao.OrderDao;
import com.amos.utils.DBUtil;
import com.amos.utils.DateUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Amos
 * @date 8/6/2020 11:03 AM
 */
public class OrderDaoImpl  implements OrderDao {
    @Override
    public int getTotal() {
        int total = 0;
        try (Connection c = DBUtil.getConnection();
             Statement s = c.createStatement()) {
            String sql = "SELECT count(*) FROM order_ WHERE deleteAt IS NULL";
            ResultSet rs = s.executeQuery(sql);
            if (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }

    @Override
    public void add(Order order) {
        String sql = "INSERT INTO order_ (`uid`,`orderCode`,`address`,`post`,`receiver`," +
                "`mobile`,`userMessage`,`createDate`,`payDate`,`deliverDate`,`confirmDate`,`status`,`sum`) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, order.getUser().getId());
            ps.setString(2, order.getOrderCode());
            ps.setString(3, order.getAddress());
            ps.setString(4, order.getPost());
            ps.setString(5, order.getReceiver());
            ps.setString(6, order.getMobile());
            ps.setString(7, order.getUserMessage());
            ps.setTimestamp(8, DateUtil.d2t(order.getCreateDate()));
            ps.setTimestamp(9, DateUtil.d2t(order.getPayDate()));
            ps.setTimestamp(10, DateUtil.d2t(order.getDeliverDate()));
            ps.setTimestamp(11, DateUtil.d2t(order.getConfirmDate()));
            ps.setString(12, order.getStatus());
            ps.setBigDecimal(13, order.getSum());
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                order.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void update(Order order) {
        String sql = "update order_ set uid=?,orderCode=?,address=?,post=?,receiver=?," +
                "mobile=?,userMessage=?,createDate=?,payDate=?,deliverDate=?,confirmDate=?,status=?,sum=?" +
                " where deleteAt is null and id = ?";
        try(Connection c = DBUtil.getConnection();
            PreparedStatement ps = c.prepareStatement(sql)){
            ps.setInt(1, order.getUser().getId());
            ps.setString(2, order.getOrderCode());
            ps.setString(3, order.getAddress());
            ps.setString(4, order.getPost());
            ps.setString(5, order.getReceiver());
            ps.setString(6, order.getMobile());
            ps.setString(7, order.getUserMessage());
            ps.setTimestamp(8, DateUtil.d2t(order.getCreateDate()));
            ps.setTimestamp(9, DateUtil.d2t(order.getPayDate()));
            ps.setTimestamp(10, DateUtil.d2t(order.getDeliverDate()));
            ps.setTimestamp(11, DateUtil.d2t(order.getConfirmDate()));
            ps.setString(12, order.getStatus());
            ps.setBigDecimal(13, order.getSum());
            ps.setInt(14, order.getId());
            ps.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    public void delete(Integer id) {
        String sql = "update order_ set deleteAt = ? where deleteAt is null and id = ?";
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
    public Order get(Integer id) {
        Order order = null;
        String sql = "select * from order_ where deleteAt is null and id=?";
        try(Connection c = DBUtil.getConnection();
            PreparedStatement ps = c.prepareStatement(sql)){
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                order = new Order();
                order.setId(rs.getInt("id"));
                order.setUser(new UserDAO().get(rs.getInt("uid")));
                order.setOrderCode(rs.getString("orderCode"));
                order.setSum(rs.getBigDecimal("sum"));
                order.setAddress(rs.getString("address"));
                order.setPost(rs.getString("post"));
                order.setReceiver(rs.getString("receiver"));
                order.setMobile(rs.getString("mobile"));
                order.setUserMessage(rs.getString("userMessage"));
                order.setCreateDate(DateUtil.t2d(rs.getTimestamp("createDate")));
                order.setPayDate(DateUtil.t2d(rs.getTimestamp("payDate")));
                order.setDeliverDate(DateUtil.t2d(rs.getTimestamp("deliverDate")));
                order.setConfirmDate(DateUtil.t2d(rs.getTimestamp("confirmDate")));
                order.setStatus(rs.getString("status"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return order;
    }

    @Override
    public List<Order> list(Integer start, Integer count) {
        List<Order> orderList = new ArrayList<>();
        String sql = "select * from order_ where deleteAt is null ORDER BY id DESC limit ?,?";
        try(Connection c = DBUtil.getConnection();
            PreparedStatement ps = c.prepareStatement(sql)){
            ps.setInt(1,start);
            ps.setInt(2,count);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Order order = new Order();
                order.setId(rs.getInt("id"));
                order.setUser(new UserDAO().get(rs.getInt("uid")));
                order.setOrderCode(rs.getString("orderCode"));
                order.setSum(rs.getBigDecimal("sum"));
                order.setAddress(rs.getString("address"));
                order.setPost(rs.getString("post"));
                order.setReceiver(rs.getString("receiver"));
                order.setMobile(rs.getString("mobile"));
                order.setUserMessage(rs.getString("userMessage"));
                order.setCreateDate(DateUtil.t2d(rs.getTimestamp("createDate")));
                order.setPayDate(DateUtil.t2d(rs.getTimestamp("payDate")));
                order.setDeliverDate(DateUtil.t2d(rs.getTimestamp("deliverDate")));
                order.setConfirmDate(DateUtil.t2d(rs.getTimestamp("confirmDate")));
                order.setStatus(rs.getString("status"));
                orderList.add(order);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return orderList;
    }

    @Override
    public List<Order> list() {
        return list(0, (int) Short.MAX_VALUE);
    }

    @Override
    public List<Order> list(Integer uid, Integer start, Integer count) {
        List<Order> orderList = new ArrayList<>();
        String sql = "select * from order_ where uid = ? and deleteAt is null ORDER BY id DESC limit ?,?";
        try(Connection c = DBUtil.getConnection();
            PreparedStatement ps = c.prepareStatement(sql)){
            ps.setInt(1,uid);
            ps.setInt(2,start);
            ps.setInt(3,count);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Order order = new Order();
                order.setId(rs.getInt("id"));
                order.setUser(new UserDAO().get(rs.getInt("uid")));
                order.setOrderCode(rs.getString("orderCode"));
                order.setAddress(rs.getString("address"));
                order.setPost(rs.getString("post"));
                order.setReceiver(rs.getString("receiver"));
                order.setSum(rs.getBigDecimal("sum"));
                order.setMobile(rs.getString("mobile"));
                order.setUserMessage(rs.getString("userMessage"));
                order.setCreateDate(DateUtil.t2d(rs.getTimestamp("createDate")));
                order.setPayDate(DateUtil.t2d(rs.getTimestamp("payDate")));
                order.setDeliverDate(DateUtil.t2d(rs.getTimestamp("deliverDate")));
                order.setConfirmDate(DateUtil.t2d(rs.getTimestamp("confirmDate")));
                order.setStatus(rs.getString("status"));
                orderList.add(order);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return orderList;
    }
}

package com.amos.dao.impl;

import com.amos.bean.OrderItem;
import com.amos.dao.OrderItemDao;
import com.amos.service.ProductService;
import com.amos.utils.DBUtil;
import com.amos.utils.DateUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Amos
 * @date 8/6/2020 11:15 AM
 */
public class OrderItemDaoImpl implements OrderItemDao {
    @Override
    public int getTotal() {
        int total = 0;
        try (Connection c = DBUtil.getConnection();
             Statement s = c.createStatement()) {
            String sql = "SELECT count(*) FROM orderitem WHERE deleteAt IS NULL";
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
    public int getTotalByProduct(Integer pid) {
        int total = 0;
        String sql = "SELECT count(*) FROM orderitem WHERE pid=? and deleteAt IS NULL";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1,pid);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }

    @Override
    public void add(OrderItem orderItem) {
        String sql = "INSERT INTO orderitem (`oid`,`pid`,`number`,`sum`) VALUES (?,?,?,?)";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, orderItem.getOrder().getId());
            ps.setInt(2, orderItem.getProduct().getId());
            ps.setInt(3,orderItem.getNumber());
            ps.setBigDecimal(4,orderItem.getSum());
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                orderItem.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void update(OrderItem orderItem) {
        String sql = "update orderitem set oid=?,pid=?,number=?,sum=? where deleteAt is null and id = ?";
        try(Connection c = DBUtil.getConnection();
            PreparedStatement ps = c.prepareStatement(sql)){
            ps.setInt(1, orderItem.getOrder().getId());
            ps.setInt(2, orderItem.getProduct().getId());
            ps.setInt(3,orderItem.getNumber());
            ps.setBigDecimal(4,orderItem.getSum());
            ps.setInt(5, orderItem.getId());
            ps.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    public void delete(Integer id) {
        String sql = "update orderitem set deleteAt = ? where deleteAt is null and id = ?";
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
    public OrderItem get(Integer id) {
        OrderItem orderItem = null;
        String sql = "select * from orderitem where deleteAt is null and id=?";
        try(Connection c = DBUtil.getConnection();
            PreparedStatement ps = c.prepareStatement(sql)){
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                orderItem = new OrderItem();
                orderItem.setId(rs.getInt("id"));
                orderItem.setOrder(new OrderDaoImpl().get(rs.getInt("oid")));
                orderItem.setProduct(new ProductDaoImpl().get(rs.getInt("uid")));
                orderItem.setNumber(rs.getInt("number"));
                orderItem.setSum(rs.getBigDecimal("sum"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return orderItem;
    }

    @Override
    public List<OrderItem> listByOrder(Integer oid, Integer start, Integer count) {
        List<OrderItem> orderItemList = new ArrayList<>();
        String sql = "select * from orderitem where oid = ? and deleteAt is null limit ?,?";
        try(Connection c = DBUtil.getConnection();
            PreparedStatement ps = c.prepareStatement(sql)){
            ps.setInt(1,oid);
            ps.setInt(2,start);
            ps.setInt(3,count);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                OrderItem orderItem = new OrderItem();
                orderItem.setId(rs.getInt("id"));
                orderItem.setOrder(new OrderDaoImpl().get(rs.getInt("oid")));
                orderItem.setProduct(new ProductService().get(rs.getInt("pid")));
                orderItem.setNumber(rs.getInt("number"));
                orderItemList.add(orderItem);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return orderItemList;
    }

    @Override
    public List<OrderItem> listByProduct(Integer pid, Integer start, Integer count) {
        List<OrderItem> orderItemList = new ArrayList<>();
        String sql = "select * from orderitem where pid = ? and deleteAt is null limit ?,?";
        try(Connection c = DBUtil.getConnection();
            PreparedStatement ps = c.prepareStatement(sql)){
            ps.setInt(1,pid);
            ps.setInt(2,start);
            ps.setInt(3,count);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                OrderItem orderItem = new OrderItem();
                orderItem.setId(rs.getInt("id"));
                orderItem.setOrder(new OrderDaoImpl().get(rs.getInt("oid")));
                orderItem.setProduct(new ProductDaoImpl().get(rs.getInt("uid")));
                orderItem.setNumber(rs.getInt("number"));
                orderItem.setSum(rs.getBigDecimal("sum"));
                orderItemList.add(orderItem);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return orderItemList;
    }
}

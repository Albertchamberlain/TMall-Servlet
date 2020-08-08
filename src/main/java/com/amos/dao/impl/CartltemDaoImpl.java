package com.amos.dao.impl;

import com.amos.bean.CartItem;
import com.amos.dao.CartItemDao;
import com.amos.dao.UserDao;
import com.amos.service.ProductService;
import com.amos.utils.DBUtil;
import com.amos.utils.DateUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Amos
 * @date 8/5/2020 8:10 PM
 */
public class CartltemDaoImpl implements CartItemDao {
    @Override
    public int getTotal(Integer uid){
        int total = 0;
        String sql ="select count(*) from cartitem where uid = ? and deleteAt is null";
        try (Connection connection = DBUtil.getConnection(); PreparedStatement preparedStatement =connection.prepareStatement(sql)){
            preparedStatement.setInt(1,uid);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                total = resultSet.getInt(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return total;
    }

    @Override
    public void add(CartItem cartItem) {
        String sql = "INSERT INTO cartitem (`uid`,`pid`,`number`,`sum`) VALUES (?,?,?,?)";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, cartItem.getUser().getId());
            ps.setInt(2, cartItem.getProduct().getId());
            ps.setInt(3,cartItem.getNumber());
            ps.setBigDecimal(4,cartItem.getSum());
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                cartItem.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void update(CartItem cartItem) {
        String sql = "update cartitem set uid=?,pid=?,number=?,sum=? where deleteAt is null and id = ?";
        try(Connection c = DBUtil.getConnection();
            PreparedStatement ps = c.prepareStatement(sql)){
            ps.setInt(1, cartItem.getUser().getId());
            ps.setInt(2, cartItem.getProduct().getId());
            ps.setInt(3,cartItem.getNumber());
            ps.setBigDecimal(4,cartItem.getSum());
            ps.setInt(5, cartItem.getId());
            ps.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    public void delete(Integer id) {
        String sql = "update cartitem set deleteAt = ? where deleteAt is null and id = ?";
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
    public CartItem get(Integer id) {
        CartItem cartItem = null;
        String sql = "select * from cartitem where deleteAt is null and id=?";
        try(Connection c = DBUtil.getConnection();
            PreparedStatement ps = c.prepareStatement(sql)){
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                cartItem = new CartItem();
                cartItem.setId(rs.getInt("id"));
                cartItem.setUser(new UserDaoImpl().get(rs.getInt("uid")));
                cartItem.setProduct(new ProductService().get(rs.getInt("pid")));
                cartItem.setNumber(rs.getInt("number"));
                cartItem.setSum(rs.getBigDecimal("sum"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return cartItem;
    }

    @Override
    public List<CartItem> listByUser(Integer uid,Integer start,Integer count) {
        List<CartItem> beans = new ArrayList<>();
        String sql = "select * from cartitem where uid = ? and deleteAt is null limit ?,?";
        try(Connection c = DBUtil.getConnection();
            PreparedStatement ps = c.prepareStatement(sql)){
            ps.setInt(1,uid);
            ps.setInt(2,start);
            ps.setInt(3,count);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                CartItem bean = new CartItem();
                bean.setId(rs.getInt("id"));
                bean.setUser(new UserDaoImpl().get(rs.getInt("uid")));
                bean.setProduct(new ProductService().get(rs.getInt("pid")));
                bean.setNumber(rs.getInt("number"));
                bean.setSum(rs.getBigDecimal("sum"));
                beans.add(bean);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return beans;

    }

    @Override
    public List<CartItem> listByUser(Integer uid) {
        return listByUser(uid,0, (int) Short.MAX_VALUE);
    }
}

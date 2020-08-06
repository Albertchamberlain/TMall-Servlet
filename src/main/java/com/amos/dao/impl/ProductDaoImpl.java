package com.amos.dao.impl;

import com.amos.bean.Product;
import com.amos.dao.ProductDao;
import com.amos.utils.DBUtil;
import com.amos.utils.DateUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Amos
 * @date 8/6/2020 1:06 PM
 */
public class ProductDaoImpl implements ProductDao {
    @Override
    public int getTotal(Integer cid) {
        int total = 0;
        String sql = "SELECT count(*) FROM product WHERE cid=? and deleteAt IS NULL";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1,cid);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                total = rs.getInt(1);
            }
        } catch ( SQLException e) {
            e.printStackTrace();
        }
        return total;
    }

    @Override
    public void add(Product product) {
        String sql = "INSERT INTO product (`cid`,`name`,`subTitle`,`originalPrice`," +
                "`nowPrice`,`stock`,`createDate`) VALUES (?,?,?,?,?,?,?)";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, product.getCategory().getId());
            ps.setString(2, product.getName());
            ps.setString(3, product.getSubTitle());
            ps.setBigDecimal(4, product.getOriginalPrice());
            ps.setBigDecimal(5, product.getNowPrice());
            ps.setInt(6, product.getStock());
            ps.setTimestamp(7, DateUtil.d2t(product.getCreateDate()));
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                product.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Product product) {
        String sql = "update product set cid=?,name=?,subTitle=? ,originalPrice=?,nowPrice=?," +
                "stock=?,createDate=? where deleteAt is null and id = ?";
        try(Connection c = DBUtil.getConnection();
            PreparedStatement ps = c.prepareStatement(sql)){
            ps.setInt(1, product.getCategory().getId());
            ps.setString(2, product.getName());
            ps.setString(3, product.getSubTitle());
            ps.setBigDecimal(4, product.getOriginalPrice());
            ps.setBigDecimal(5, product.getNowPrice());
            ps.setInt(6, product.getStock());
            ps.setTimestamp(7, DateUtil.d2t(product.getCreateDate()));
            ps.setInt(8, product.getId());
            ps.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    public void delete(Integer id) {
        String sql = "update product set deleteAt = ? where deleteAt is null and id = ?";
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
    public Product get(Integer id) {
        Product product = null;
        String sql = "select * from product where deleteAt is null and id=?";
        try(Connection c = DBUtil.getConnection();
            PreparedStatement ps = c.prepareStatement(sql)){
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                product = new Product();
                product.setId(rs.getInt("id"));
                product.setCategory(new CategoryDaoImpl().get(rs.getInt("cid")));
                product.setName(rs.getString("name"));
                product.setSubTitle(rs.getString("subTitle"));
                product.setOriginalPrice(rs.getBigDecimal("originalPrice"));
                product.setNowPrice(rs.getBigDecimal("nowPrice"));
                product.setStock(rs.getInt("stock"));
                product.setCreateDate(DateUtil.t2d(rs.getTimestamp("createDate")));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return product;
    }

    @Override
    public List<Product> listByCategory(Integer cid, Integer start, Integer count) {
        List<Product> productList = new ArrayList<>();
        String sql = "select * from product where cid = ? and deleteAt is null ORDER BY id desc limit ?,?";
        try(Connection c = DBUtil.getConnection();
            PreparedStatement ps = c.prepareStatement(sql)){
            ps.setInt(1,cid);
            ps.setInt(2,start);
            ps.setInt(3,count);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setCategory(new CategoryDaoImpl().get(rs.getInt("cid")));
                product.setName(rs.getString("name"));
                product.setSubTitle(rs.getString("subTitle"));
                product.setOriginalPrice(rs.getBigDecimal("originalPrice"));
                product.setNowPrice(rs.getBigDecimal("nowPrice"));
                product.setStock(rs.getInt("stock"));
                product.setCreateDate(DateUtil.t2d(rs.getTimestamp("createDate")));
                productList.add(product);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return productList;
    }

    @Override
    public List<Product> listBySearch(String keyword, Integer start,Integer count) {
        List<Product> productList = new ArrayList<>();
        if(null==keyword || 0==keyword.trim().length()) {
            return productList;
        }
        String sql = "select * from product where name like ? and deleteAt  is null ORDER BY id desc limit ?,?";
        try(Connection c = DBUtil.getConnection();
            PreparedStatement ps = c.prepareStatement(sql)){
            ps.setString(1,"%"+keyword.trim()+"%");
            ps.setInt(2,start);
            ps.setInt(3,count);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setCategory(new CategoryDaoImpl().get(rs.getInt("cid")));
                product.setName(rs.getString("name"));
                product.setSubTitle(rs.getString("subTitle"));
                product.setOriginalPrice(rs.getBigDecimal("originalPrice"));
                product.setNowPrice(rs.getBigDecimal("nowPrice"));
                product.setStock(rs.getInt("stock"));
                product.setCreateDate(DateUtil.t2d(rs.getTimestamp("createDate")));
                productList.add(product);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return productList;
    }
}

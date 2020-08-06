package com.amos.dao.impl;

import com.amos.bean.ProductImage;
import com.amos.dao.ProductImageDao;
import com.amos.utils.DBUtil;
import com.amos.utils.DateUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Amos
 * @date 8/6/2020 5:58 PM
 */
public class ProductImageDaoImpl implements ProductImageDao {
    @Override
    public Integer getTotal() {
        Integer total = null;
        try (Connection c = DBUtil.getConnection();
             Statement s = c.createStatement()) {
            String sql = "select count(*) from product_image where deleteAt is null";
            ResultSet rs = s.executeQuery(sql);
            if(rs.next()){
                total = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }

    @Override
    public void add(ProductImage productImage) {

        String sql = "insert into product_image (`pid`,`type`) values (?,?)";
        try(Connection c = DBUtil.getConnection();
            PreparedStatement ps = c.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)){
            ps.setInt(1,productImage.getProduct().getId());
            ps.setString(2, productImage.getType());
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()){
                int id = rs.getInt(1);
                productImage.setId(id);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void update(ProductImage productImage) {
        delete(productImage.getId());
        add(productImage);
    }

    @Override
    public void delete(Integer id) {
        String sql = "update product_image set deleteAt = ? where deleteAt is null and id = ?";
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
    public List<ProductImage> list(Integer pid, String type, Integer start, Integer count) {
        List<ProductImage> productImageList = new ArrayList<>();
        String sql = "select * from product_image where pid = ? and type = ? and deleteAt is null limit ?,?";
        try(Connection c = DBUtil.getConnection();
            PreparedStatement ps = c.prepareStatement(sql)){
            ps.setInt(1,pid);
            ps.setString(2,type);
            ps.setInt(3,start);
            ps.setInt(4,count);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                ProductImage productImage = new ProductImage();
                productImage.setId(rs.getInt("id"));
                productImage.setType(rs.getString("type"));
                productImage.setProduct(new ProductDaoImpl().get(rs.getInt("pid")));
                productImageList.add(productImage);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return productImageList;
    }

    @Override
    public ProductImage get(Integer id) {
        ProductImage productImage = null;
        String sql = "select * from product_image where deleteAt is null and id=?";
        try(Connection c = DBUtil.getConnection();
            PreparedStatement ps = c.prepareStatement(sql)){
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                productImage = new ProductImage();
                productImage.setId(rs.getInt("id"));
                productImage.setType(rs.getString("type"));
                productImage.setProduct(new ProductDaoImpl().get(rs.getInt("pid")));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return productImage;
    }
}

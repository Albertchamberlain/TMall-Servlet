package com.amos.dao.impl;

import com.amos.bean.Category;
import com.amos.dao.CategoryDao;
import com.amos.utils.DBUtil;
import com.amos.utils.DateUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Amos
 * @date 8/5/2020 9:01 PM
 */
public class CategoryImpl  implements CategoryDao {
    @Override
    public int getTotal() {
        Integer total = 0;
        try (Connection c = DBUtil.getConnection();
             Statement s = c.createStatement()) {
            String sql = "select count(*) from category where deleteAt is null";
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
    public void add(Category category) {
        String sql = "insert into category (name,recommend) values (?,?)";
        try(Connection c = DBUtil.getConnection();
            PreparedStatement ps = c.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)){
            ps.setString(1,category.getName());
            ps.setInt(2,category.getRecommend());
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()){
                Integer id = rs.getInt(1);
                category.setId(id);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void update(Category category) {
        String sql = "update category set name = ? ,recommend = ? where deleteAt is null and id = ?";
        try(Connection c = DBUtil.getConnection();
            PreparedStatement ps = c.prepareStatement(sql)){
            ps.setString(1,category.getName());
            ps.setInt(2,category.getRecommend());
            ps.setInt(3,category.getId());
            ps.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    public void delete(Integer id) {
        String sql = "update category set deleteAt = ? where deleteAt is null and id = ?";
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
    public Category get(Integer id) {
        Category category = null;
        String sql = "select * from category where deleteAt is null and id=?";
        try(Connection c = DBUtil.getConnection();
            PreparedStatement ps = c.prepareStatement(sql)){
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                category = new Category();
                category.setId(rs.getInt("id"));
                category.setName(rs.getString("name"));
                category.setRecommend(rs.getInt("recommend"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return category;
    }

    @Override
    public List<Category> list(Integer start, Integer count) {
        List<Category> categoryList = new ArrayList<>();
        String sql = "select * from category where deleteAt is null ORDER BY recommend DESC , id DESC limit ?,?";
        try(Connection c = DBUtil.getConnection();
            PreparedStatement ps = c.prepareStatement(sql)){
            ps.setInt(1,start);
            ps.setInt(2,count);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Category category = new Category();
                category.setId(rs.getInt("id"));
                category.setName(rs.getString("name"));
                category.setRecommend(rs.getInt("recommend"));
                categoryList.add(category);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return categoryList;
    }
}

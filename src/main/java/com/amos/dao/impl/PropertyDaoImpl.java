package com.amos.dao.impl;

import com.amos.bean.Property;
import com.amos.dao.PropertyDao;
import com.amos.utils.DBUtil;
import com.amos.utils.DateUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Amos
 * @date 8/6/2020 6:16 PM
 */
public class PropertyDaoImpl  implements PropertyDao {
    @Override
    public Integer getTotal(Integer cid) {
        Integer total = 0;
        String sql = "SELECT count(*) FROM property WHERE cid=? and deleteAt IS NULL";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1,cid);
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
    public void add(Property property) {
        String sql = "INSERT INTO property (cid,name) VALUES (?,?)";
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, property.getCategory().getId());
            ps.setString(2, property.getName());
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                property.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Property property) {
        String sql = "UPDATE property SET cid = ?, name = ? WHERE deleteAt IS NULL AND id = ?";
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, property.getCategory().getId());
            ps.setString(2, property.getName());
            ps.setInt(3, property.getId());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void delete(int id) {
        String sql = "UPDATE property SET deleteAt = ? WHERE deleteAt IS NULL AND id = ?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setTimestamp(1, DateUtil.nowTimestamp());
            ps.setInt(2, id);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Property get(int id) {
        Property property = null;
        String sql = "SELECT * FROM property WHERE deleteAt IS NULL AND id=?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                property = new Property();
                property.setId(rs.getInt("id"));
                property.setName(rs.getString("name"));
                property.setCategory(new CategoryDaoImpl().get(rs.getInt("cid")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return property;
    }

    @Override
    public List<Property> list(int cid, int start, int count) {
        List<Property> propertyList = new ArrayList<>();
        String sql = "SELECT * FROM property WHERE cid=? and deleteAt IS NULL limit ?,?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, cid);
            ps.setInt(2, start);
            ps.setInt(3, count);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Property property = new Property();
                property.setId(rs.getInt("id"));
                property.setCategory(new CategoryDaoImpl().get(rs.getInt("cid")));
                property.setName(rs.getString("name"));
                propertyList.add(property);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return propertyList;
    }
}

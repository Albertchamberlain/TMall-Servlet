package com.amos.dao.impl;

import com.amos.bean.PropertyValue;
import com.amos.dao.PropertyValueDao;
import com.amos.utils.DBUtil;
import com.amos.utils.DateUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Amos
 * @date 8/6/2020 10:06 PM
 */
public class PropertyValueDaoImpl  implements PropertyValueDao {

    @Override
    public Integer getTotal() {
        Integer total = 0;
        String sql = "SELECT count(*) FROM property_value WHERE deleteAt IS NULL";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement s = c.prepareStatement(sql)) {
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
    public void add(PropertyValue propertyValue) {
        String sql = "INSERT INTO property_value (`pid`,`ptid`,`value`) VALUES (?,?,?)";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, propertyValue.getProduct().getId());
            ps.setInt(2, propertyValue.getProperty().getId());
            ps.setString(3, propertyValue.getValue());
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                propertyValue.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void update(PropertyValue propertyValue) {
        String sql = "update property_value set pid = ? , ptid = ? ,value = ? where deleteAt is null and id = ?";
        try(Connection c = DBUtil.getConnection();
            PreparedStatement ps = c.prepareStatement(sql)){
            ps.setInt(1, propertyValue.getProduct().getId());
            ps.setInt(2, propertyValue.getProperty().getId());
            ps.setString(3, propertyValue.getValue());
            ps.setInt(4,propertyValue.getId());
            ps.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String sql = "update property_value set deleteAt = ? where deleteAt is null and id = ?";
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
    public PropertyValue get(int id) {
        PropertyValue propertyValue = null;
        String sql = "select * from property_value where deleteAt is null and id=?";
        try(Connection c = DBUtil.getConnection();
            PreparedStatement ps = c.prepareStatement(sql)){
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                propertyValue = new PropertyValue();
                propertyValue.setId(rs.getInt("id"));
                propertyValue.setValue(rs.getString("value"));
                propertyValue.setProduct(new ProductDaoImpl().get(rs.getInt("pid")));
                propertyValue.setProperty(new PropertyDaoImpl().get(rs.getInt("ptid")));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return propertyValue;
    }

    @Override
    public PropertyValue get(int ptid, int pid) {
        PropertyValue propertyValue = null;
        String sql = "select * from property_value where deleteAt is null and pid=? and ptid = ?";
        try(Connection c = DBUtil.getConnection();
            PreparedStatement ps = c.prepareStatement(sql)){
            ps.setInt(1,pid);
            ps.setInt(2,ptid);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                propertyValue = new PropertyValue();
                propertyValue.setId(rs.getInt("id"));
                propertyValue.setValue(rs.getString("value"));
                propertyValue.setProduct(new ProductDaoImpl().get(rs.getInt("pid")));
                propertyValue.setProperty(new PropertyDaoImpl().get(rs.getInt("ptid")));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return propertyValue;
    }

    @Override
    public List<PropertyValue> list(int pid) {
        List<PropertyValue> propertyValueList = new ArrayList<>();
        String sql = "select * from property_value where `pid`=? and deleteAt is null";
        try(Connection c = DBUtil.getConnection();
            PreparedStatement ps = c.prepareStatement(sql)){
            ps.setInt(1,pid);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                PropertyValue propertyValue = new PropertyValue();
                propertyValue.setId(rs.getInt("id"));
                propertyValue.setValue(rs.getString("value"));
                propertyValue.setProduct(new ProductDaoImpl().get(rs.getInt("pid")));
                propertyValue.setProperty(new PropertyDaoImpl().get(rs.getInt("ptid")));
                propertyValueList.add(propertyValue);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return propertyValueList;
    }

    @Override
    public List<PropertyValue> list(int start, int count) {
        List<PropertyValue> propertyValueList = new ArrayList<>();
        String sql = "select * from property_value where deleteAt is null limit ?,?";
        try(Connection c = DBUtil.getConnection();
            PreparedStatement ps = c.prepareStatement(sql)){
            ps.setInt(1,start);
            ps.setInt(2,count);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                PropertyValue propertyValue = new PropertyValue();
                propertyValue.setId(rs.getInt("id"));
                propertyValue.setValue(rs.getString("value"));
                propertyValue.setProduct(new ProductDaoImpl().get(rs.getInt("pid")));
                propertyValue.setProperty(new PropertyDaoImpl().get(rs.getInt("ptid")));
                propertyValueList.add(propertyValue);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return propertyValueList;
    }
}

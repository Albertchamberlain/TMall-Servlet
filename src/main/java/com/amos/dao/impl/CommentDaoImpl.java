package com.amos.dao.impl;

import com.amos.bean.Comment;
import com.amos.dao.CommentDao;
import com.amos.utils.DBUtil;
import com.amos.utils.DateUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Amos
 * @date 8/6/2020 10:51 AM
 */
public class CommentDaoImpl implements CommentDao {
    @Override
    public int getTotal() {
        int total = 0;
        try (Connection c = DBUtil.getConnection();
             Statement s = c.createStatement()) {
            String sql = "SELECT count(*) FROM comment WHERE deleteAt IS NULL";
            ResultSet rs = s.executeQuery(sql);
            if (rs.next()) {
                total = rs.getInt(1);
            }
        } catch ( SQLException e) {
            e.printStackTrace();
        }
        return total;
    }

    @Override
    public int getTotalByProduct(Integer pid) {
        int total = 0;
        String sql = "SELECT count(*) FROM comment WHERE pid=? and deleteAt IS NULL";
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
    public void add(Comment comment) {
        String sql = "INSERT INTO comment (`pid`,`uid`,`content`,`createDate`) VALUES (?,?,?,?)";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, comment.getProduct().getId());
            ps.setInt(2, comment.getUser().getId());
            ps.setString(3, comment.getContent());
            ps.setTimestamp(4, DateUtil.d2t(comment.getCreateDate()));
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                comment.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void update(Comment comment) {
        String sql = "update comment set pid = ? , uid = ? ,content = ?,createDate = ? where deleteAt is null and id = ?";
        try(Connection c = DBUtil.getConnection();
            PreparedStatement ps = c.prepareStatement(sql)){
            ps.setInt(1, comment.getProduct().getId());
            ps.setInt(2, comment.getUser().getId());
            ps.setString(3, comment.getContent());
            ps.setTimestamp(4, DateUtil.d2t(comment.getCreateDate()));
            ps.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    public void delete(Integer id) {
        String sql = "update comment set deleteAt = ? where deleteAt is null and id = ?";
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
    public Comment get(Integer id) {
        Comment comment = null;
        String sql = "select * from comment where deleteAt is null and id=?";
        try(Connection c = DBUtil.getConnection();
            PreparedStatement ps = c.prepareStatement(sql)){
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                comment = new Comment();
                comment.setId(rs.getInt("id"));
                comment.setUser(new UserDaoImpl().get(rs.getString("value")));
                comment.setProduct(new ProductDaoImpl().get(rs.getInt("pid")));
                comment.setContent(rs.getString("content"));
                comment.setCreateDate(DateUtil.t2d(rs.getTimestamp("createDate")));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return comment;
    }

    @Override
    public Comment get(Integer pid, Integer uid) {
        Comment comment = null;
        String sql = "select * from comment where deleteAt is null and pid=? and uid = ?";
        try(Connection c = DBUtil.getConnection();
            PreparedStatement ps = c.prepareStatement(sql)){
            ps.setInt(1,pid);
            ps.setInt(2,uid);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                comment = new Comment();
                comment.setId(rs.getInt("id"));
                comment.setUser(new UserDaoImpl().get(rs.getString("value")));
                comment.setProduct(new ProductDaoImpl().get(rs.getInt("pid")));
                comment.setContent(rs.getString("content"));
                comment.setCreateDate(DateUtil.t2d(rs.getTimestamp("createDate")));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return comment;
    }

    @Override
    public List<Comment> list(Integer pid, Integer start, Integer count) {
        List<Comment> commentList = new ArrayList<>();
        String sql = "select * from comment where `pid`=? and deleteAt is null ORDER BY id DESC limit ?,?";
        try(Connection c = DBUtil.getConnection();
            PreparedStatement ps = c.prepareStatement(sql)){
            ps.setInt(1,pid);
            ps.setInt(2,start);
            ps.setInt(3,count);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Comment comment = new Comment();
                comment.setId(rs.getInt("id"));
                comment.setUser(new UserDaoImpl().get(rs.getInt("uid")));
                comment.setProduct(new ProductDaoImpl().get(rs.getInt("pid")));
                comment.setContent(rs.getString("content"));
                comment.setCreateDate(DateUtil.t2d(rs.getTimestamp("createDate")));
                commentList.add(comment);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return commentList;
    }
}

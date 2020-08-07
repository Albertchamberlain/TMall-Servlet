package com.amos.service;

import com.amos.bean.Comment;
import com.amos.dao.CommentDao;
import com.amos.dao.impl.CommentDaoImpl;

import java.util.List;

/**
 * @author Amos
 * @date 8/7/2020 10:38 AM
 */
public class CommentService {
    private CommentDao commentDao = new CommentDaoImpl();
    public int getTotalByProduct(int pid){
        return commentDao.getTotalByProduct(pid);
    }
    public void add(Comment comment) {
        commentDao.add(comment);
    }
    public void update(Comment comment) {
        commentDao.update(comment);
    }
    public void delete(int id) {
        commentDao.delete(id);
    }
    public Comment get(int id){
        return commentDao.get(id);
    }
    public Comment get(int pid,int uid){
        return commentDao.get(pid,uid);
    }
    public List<Comment> list(int pid, int start , int count){
        List<Comment> result = commentDao.list(pid,0,count);
        for(Comment c : result){
            //使用户名变为匿名
            String name = c.getUser().getName();
            char[] chars = name.toCharArray();
            for(int i=1;i<chars.length-1;i++){
                chars[i] = '*';
            }
            c.getUser().setName(new String(chars));
        }
        return result;
    }
    public List<Comment> list(int pid){
        return this.list(pid,0,Short.MAX_VALUE);
    }
}

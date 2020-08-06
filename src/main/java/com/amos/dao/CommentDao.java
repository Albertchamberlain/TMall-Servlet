package com.amos.dao;

import com.amos.bean.Comment;

import java.util.List;

/**
 * @author Amos
 * @date 8/6/2020 10:44 AM
 */
public interface CommentDao {
    /**
     * get all comment
     * @return
     */
    int getTotal();

    /**
     * Get all comment by product id
     * @param pid
     * @return
     */
    int getTotalByProduct(Integer pid);

    /**
     * add comment
     * @param comment
     */
    void add(Comment comment);

    /**
     * update comment
     * @param comment
     */
    void update(Comment comment);

    /**
     * delete comment by id
     * @param id
     */
    void delete(Integer id);

    /**
     * get comment by id
     * @param id
     * @return
     */
    Comment get(Integer id);

    /**
     * get comment by pid&uid
     * @param pid
     * @param uid
     * @return
     */
    Comment get(Integer pid,Integer uid);


    /**
     * get some comment
     * @param pid
     * @param start
     * @param count
     * @return List<Comment>
     */
    List<Comment> list(Integer pid, Integer start , Integer count);
}

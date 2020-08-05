package com.amos.dao;

import com.amos.bean.CartItem;
import com.amos.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Amos
 * @date 8/5/2020 8:02 PM
 */
public interface CartItemDao {
    /**
     * Get Total
     * @param uid
     * @return
     */
     int getTotal(Integer uid);

    /**
     * add cartitem
     * @param cartItem
     */
    void add(CartItem cartItem);

    /**
     * update cartItem
     * @param cartItem
     */
    void update(CartItem cartItem);

    /**
     * delete cartitem
     * @param id
     */
    void delete(Integer id);

    /**
     * get cartitem
     * @param id
     * @return
     */
    CartItem get(Integer id);

    /**
     * Overload listByUser
     * @param uid
     * @return
     */
    List<CartItem> listByUser(Integer uid,Integer start,Integer count);

    /**
     * get list by user with limit
     * @return
     */
    List<CartItem> listByUser(Integer uid);


}

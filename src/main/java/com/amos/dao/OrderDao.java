package com.amos.dao;

import com.amos.bean.Order;

import java.util.List;

/**
 * @author Amos
 * @date 8/6/2020 10:58 AM
 */
public interface OrderDao {
    /**
     * get count order
     * @return
     */
    int getTotal();

    /**
     * add order
     * @param order
     */
    void add(Order order);

    /**
     * update order
     * @param order
     */
    void update(Order order);

    /**
     * delete order by id
     * @param id
     */
    void delete(Integer id);

    /**
     * get order by id
     * @param id
     * @return
     */
    Order get(Integer id);


    /**
     * get some order
     * @param start
     * @param count
     * @return
     */
    List<Order> list(Integer start , Integer count);

    /**
     * get all order with reload list()Fun
     * @return
     */
    List<Order> list();


    /**
     * get some order limit uid
     * @param uid
     * @param start
     * @param count
     * @return
     */
    List<Order> list(Integer uid,Integer start ,Integer count);
}

package com.amos.dao;

import com.amos.bean.OrderItem;

import java.util.List;

/**
 * @author Amos
 * @date 8/6/2020 11:11 AM
 */
public interface OrderItemDao {
    /**
     * get count orderitem
     * @return
     */
    int getTotal();

    /**
     * get count orderitem by pid
     * @param pid
     * @return
     */
    int getTotalByProduct(Integer pid);

    /**
     * add orderitem
     * @param orderItem
     */
    void add(OrderItem orderItem);

    /**
     * update orderitem
     * @param orderItem
     */
    void update(OrderItem orderItem);

    /**
     * delete orderitem
     * @param id
     */
    void delete(Integer id);

    /**
     * get orderitem by id
     * @param id
     * @return
     */
    OrderItem get(Integer id);

    /**
     * get some orderitem by oid
     * @param oid
     * @param start
     * @param count
     * @return
     */
    List<OrderItem> listByOrder(Integer oid, Integer start , Integer count);

    /**
     * get some orderitem by pid
     * @param pid
     * @param start
     * @param count
     * @return
     */
    List<OrderItem> listByProduct(Integer pid, Integer start , Integer count);

}

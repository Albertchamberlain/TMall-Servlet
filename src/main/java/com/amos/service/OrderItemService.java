package com.amos.service;

import com.amos.bean.OrderItem;
import com.amos.dao.OrderItemDao;
import com.amos.dao.impl.OrderItemDaoImpl;

import java.util.List;

/**
 * @author Amos
 * @date 8/7/2020 9:11 PM
 */
public class OrderItemService {

    private OrderItemDao dao = new OrderItemDaoImpl();

    public int getTotalByProduct(int pid){
        return dao.getTotalByProduct(pid);
    }
    public void add(OrderItem orderItem) {
        dao.add(orderItem);
    }
    public void update(OrderItem orderItem) {
        dao.update(orderItem);
    }
    public void delete(int id) {
        dao.delete(id);
    }
    public OrderItem get(int id){
        return dao.get(id);
    }
    public List<OrderItem> listByOrder(int oid, int start , int count){
        return dao.listByOrder(oid,start,count);
    }
    public List<OrderItem> listByOrder(int oid){
        return dao.listByOrder(oid,0, (int) Short.MAX_VALUE);
    }
    public List<OrderItem> listByProduct(int pid, int start , int count){
        return dao.listByProduct(pid,start,count);
    }
    public List<OrderItem> listByProduct(int pid){
        return dao.listByProduct(pid,0, (int) Short.MAX_VALUE);
    }
}

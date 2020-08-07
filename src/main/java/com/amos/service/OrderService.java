package com.amos.service;

import com.amos.bean.Order;
import com.amos.bean.OrderItem;
import com.amos.dao.OrderDao;
import com.amos.dao.impl.OrderDaoImpl;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Amos
 * @date 8/7/2020 9:13 PM
 */
public class OrderService {
    public static class OrderType {
        public static final String WAIT_PAY = "waitPay";
        public static final String WAIT_DELIVERY = "waitDelivery";
        public static final String WAIT_CONFIRM = "waitConfirm";
        public static final String WAIT_REVIEW = "waitReview";
        public static final String FINISH = "finish";
    }
    private OrderDao dao = new OrderDaoImpl();
    public int getTotal() {return dao.getTotal();}
    public void add(Order order) {
        dao.add(order);
    }
    public void update(Order order) {
        dao.update(order);
    }
    public void delete(int id) {
        dao.delete(id);
    }

    private void fill(Order rawOrder){
        List<OrderItem> orderItemList = new OrderItemService().listByOrder(rawOrder.getId());
        BigDecimal totalPrice = new BigDecimal(0);
        int totalNumber = 0;
        for(OrderItem orderItem:orderItemList){
            totalNumber += orderItem.getNumber();
            BigDecimal thisPrice =  new BigDecimal(orderItem.getNumber()).multiply(orderItem.getProduct().getNowPrice());
            totalPrice = totalPrice.add(thisPrice);
        }
        rawOrder.setOrderItems(orderItemList);
        rawOrder.setTotalNumber(totalNumber);
        rawOrder.setTotalPrice(totalPrice);
    }

    public Order get(int id){
        Order o =  dao.get(id);
        fill(o);
        return o;
    }

    public List<Order> list(int uid, int start , int count){
        List<Order> orderList =  dao.list(uid,start,count);
        for(Order o : orderList){
            fill(o);
        }
        return orderList;
    }

    public List<Order> list(int start , int count){
        List<Order> orderList =  dao.list(start,count);
        for(Order o : orderList){
            fill(o);
        }
        return orderList;
    }
    public List<Order> list(int uid){
        return this.list(uid,0,Short.MAX_VALUE);
    }
    public List<Order> list(){
        return dao.list(0, (int) Short.MAX_VALUE);
    }
}

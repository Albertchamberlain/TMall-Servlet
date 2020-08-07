package com.amos.service;

import com.amos.bean.CartItem;
import com.amos.dao.CartItemDao;
import com.amos.dao.impl.CartltemDaoImpl;

import java.util.List;

/**
 * @author Amos
 * @date 8/7/2020 9:58 AM
 */
public class CartItemService {
    private CartItemDao cartItemDao = new CartltemDaoImpl();
    public Integer getTotal (int uid){
        return cartItemDao.getTotal(uid);
    }

    public void add (CartItem cartItem){
        cartItemDao.add(cartItem);
    }

    public void update(CartItem cartItem){
        cartItemDao.update(cartItem);
    }

    public void delete(int id ){
        cartItemDao.delete(id);
    }


    public CartItem get(int id){
        return cartItemDao.get(id);
    }

    public List<CartItem> listByUser (int uid, int start ,int count){
        return cartItemDao.listByUser(uid,start,count);
    }

    public List<CartItem> listByUser(int uid){
        return cartItemDao.listByUser(uid,0, (int) Short.MAX_VALUE);
    }
}

package com.amos.dao;

import com.amos.bean.Product;

import java.util.List;

/**
 * @author Amos
 * @date 8/6/2020 1:00 PM
 */
public interface ProductDao {
    /**
     * get total product
     * @param cid
     * @return
     */
    int getTotal(Integer cid);

    /**
     * add product
     * @param product
     */
    void add(Product product);

    /**
     * update product
     * @param product
     */
    void update(Product product);

    /**
     * delete product
     * @param id
     */
    void delete(Integer id);

    /**
     * get product by id
     * @param id
     * @return
     */
    Product get(Integer id);

    /**
     * get some product by id
     * @param cid
     * @param start
     * @param count
     * @return
     */
    List<Product> listByCategory(Integer cid, Integer start , Integer count);

    /**
     * get product by keyword
     * @param keyword
     * @param start
     * @param count
     * @return
     */
    List<Product> listBySearch(String keyword, Integer start , Integer count);

}

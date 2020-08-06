package com.amos.dao;

import com.amos.bean.ProductImage;

import java.util.List;

/**
 * @author Amos
 * @date 8/6/2020 5:53 PM
 */
public interface ProductImageDao {

    /**
     * get count
     * @return
     */
    Integer getTotal();

    /**
     * add productimage
     * @param productImage
     */
    void add(ProductImage productImage);

    /**
     * update  productImage
     * @param productImage
     */
    void update(ProductImage productImage);

    /**
     * Ddelete productImage
     * @param id
     */
    void delete(Integer id);

    /**
     * get some productImage
     * @param pid
     * @param type
     * @param start
     * @param count
     * @return
     */

    List<ProductImage> list(Integer pid , String type, Integer start , Integer count);

    /**
     * get productImage by id
     * @param id
     * @return
     */
    ProductImage get(Integer id);


}

package com.amos.dao;

import com.amos.bean.Property;

import java.util.List;

/**
 * @author Amos
 * @date 8/6/2020 6:04 PM
 */
public interface PropertyDao {

    /**
     * get count property
     * @param cid
     * @return
     */
    Integer getTotal(Integer cid);

    /**
     * add property
     * @param property
     */

    void add(Property property);

    /**
     * update property
     * @param property
     */

    void update(Property property);

    /**
     * delete property
     * @param id
     */
    void delete(int id);


    /**
     * get property by id
     * @param id
     * @return
     */
    Property get(int id);

    /**
     * get some property
     * @param cid
     * @param start
     * @param count
     * @return
     */

    List<Property> list(int cid, int start , int count);

}

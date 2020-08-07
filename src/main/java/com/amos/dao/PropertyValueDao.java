package com.amos.dao;

import com.amos.bean.PropertyValue;

import java.util.List;

/**
 * @author Amos
 * @date 8/6/2020 10:00 PM
 */
public interface PropertyValueDao {
    /**
     * get count propertyvalue
     * @return integer
     */
    Integer getTotal();

    /**
     * add propertyvalue
     * @param bean
     */
    void add(PropertyValue bean);

    /**
     * update propertyvalue
     * @param bean
     */
    void update(PropertyValue bean);

    /**
     * delete propertyvalue
     * @param id
     */
    void delete(int id);

    /**
     * get propertyvalue by id
     * @param id
     * @return
     */
    PropertyValue get(int id);

    /**
     * get propertyvalue by ptid and pid
     * @param ptid
     * @param pid
     * @return
     */
    PropertyValue get(int ptid,int pid);

    /**
     * get some propertyvalue by id
     * @param pid
     * @return
     */
    List<PropertyValue> list(int pid);

    /**
     * get propertyvalue limit with start and count
     * @param start
     * @param count
     * @return
     */
    List<PropertyValue> list(int start , int count);

}

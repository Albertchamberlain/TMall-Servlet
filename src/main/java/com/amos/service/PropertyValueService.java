package com.amos.service;

import com.amos.bean.PropertyValue;
import com.amos.dao.PropertyValueDao;
import com.amos.dao.impl.PropertyValueDaoImpl;

/**
 * @author Amos
 * @date 8/7/2020 9:27 PM
 */
public class PropertyValueService {
    private PropertyValueDao dao = new PropertyValueDaoImpl();
    public void add(PropertyValue propertyValue) {
        dao.add(propertyValue);
    }
    public void update(PropertyValue propertyValue) {
        dao.update(propertyValue);
    }
    public void delete(int id) {
        dao.delete(id);
    }
    public PropertyValue get(int id){
        return dao.get(id);
    }
    public PropertyValue get(int ptid,int pid){
        return dao.get(ptid,pid);
    }
}

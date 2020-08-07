package com.amos.service;

import com.amos.bean.Product;
import com.amos.bean.Property;
import com.amos.bean.PropertyValue;
import com.amos.dao.PropertyDao;
import com.amos.dao.impl.PropertyDaoImpl;
import com.amos.dao.impl.PropertyValueDaoImpl;

import java.util.HashMap;
import java.util.List;

/**
 * @author Amos
 * @date 8/7/2020 9:25 PM
 */
public class PropertyService {

    private PropertyDao dao = new PropertyDaoImpl();
    public int getTotal(int cid) {return dao.getTotal(cid);}
    public void add(Property property) {
        dao.add(property);
    }
    public void update(Property property) {
        dao.update(property);
    }
    public void delete(int id) {
        dao.delete(id);
    }
    public Property get(int id){
        return dao.get(id);
    }
    public List<Property> list(int cid, int start , int count){
        return dao.list(cid,start,count);
    }
    public List<Property> list(int cid){return dao.list(cid,0,Short.MAX_VALUE);}
    public HashMap<Property, PropertyValue> list(Product p){
        HashMap<Property,PropertyValue> result = new HashMap<>();
        List<Property> PropertiesInCategory = this.list(p.getCategory().getId());
        for (Property property: PropertiesInCategory) {
            PropertyValue value = new PropertyValueDaoImpl().get(property.getId(),p.getId());
            if(null==value){
                value = new PropertyValue();
                value.setProduct(p);
                value.setProperty(property);
            }
            result.put(property,value);
        }
        return result;
    }
}

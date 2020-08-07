package com.amos.service;

import com.amos.bean.Category;
import com.amos.bean.Product;
import com.amos.dao.CategoryDao;
import com.amos.dao.impl.CategoryDaoImpl;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @author Amos
 * @date 8/7/2020 10:33 AM
 */
public class CategoryService {
    private CategoryDao categoryDao = new CategoryDaoImpl();
    public int getTotal() {return categoryDao.getTotal();}
    public void add(Category bean) {
        categoryDao.add(bean);
    }
    public void update(Category bean) {
        categoryDao.update(bean);
    }
    public void delete(int id) {
        categoryDao.delete(id);
    }
    public Category get(int id){
        return categoryDao.get(id);
    }
    public List<Category> list(int start , int count){
        return categoryDao.list(start,count);
    }
    public List<Category> list(){
        return categoryDao.list(0, (int) Short.MAX_VALUE);
    }
    public List<Category> listInHome(){
        List<Category> categories = this.list();
        for(Category c:categories){
            List<Product> products = new ProductService().listByCategory(c.getId(),0,64);
            for(Product product:products){
                String subTitle = product.getSubTitle();
                String shortTitle = StringUtils.substringBetween(subTitle," ");
                if(shortTitle==null){
                    shortTitle = subTitle;
                }
                product.setSubTitle(shortTitle);
            }
            c.setProducts(products);
        }
        return categories;
    }
}

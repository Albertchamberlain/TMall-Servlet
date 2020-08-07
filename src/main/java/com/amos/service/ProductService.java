package com.amos.service;

import com.amos.bean.Product;
import com.amos.dao.ProductDao;
import com.amos.dao.impl.ProductDaoImpl;

import java.util.List;

/**
 * @author Amos
 * @date 8/7/2020 9:23 PM
 */
public class ProductService {
    private ProductDao dao = new ProductDaoImpl();
    public int getTotal(int cid){return dao.getTotal(cid);}
    public void add(Product product) {
        dao.add(product);
    }
    public void update(Product product) {
        dao.update(product);
    }
    public void delete(int id) {
        dao.delete(id);
    }
    public Product get(int id){
        Product product = dao.get(id);
        fill(product);
        return product;
    }
    private void fill(Product rawProduct){
        rawProduct.setFirstProductImage(new ProductImageService().getFirstImage(rawProduct.getId()));
        rawProduct.setSaleCount(new OrderItemService().getTotalByProduct(rawProduct.getId()));
        rawProduct.setCommentCount(new CommentService().getTotalByProduct(rawProduct.getId()));
    }
    private void fill(List<Product> rawProductList){
        for(Product rawProduct : rawProductList){
            fill(rawProduct);
        }
    }
    public List<Product> listByCategory(int cid, int start , int count){
        List<Product> products =  dao.listByCategory(cid,start,count);
        fill(products);
        return products;
    }
    public List<Product> listByCategory(int cid){
        return this.listByCategory(cid,0,Short.MAX_VALUE);
    }
    public List<Product> listBySearch(String keyword, int start , int count){
        List<Product> products = dao.listBySearch(keyword,start , count);
        fill(products);
        return products;
    }
    public List<Product> listBySearch(String keyword){
        return this.listBySearch(keyword,0,Short.MAX_VALUE);
    }
}

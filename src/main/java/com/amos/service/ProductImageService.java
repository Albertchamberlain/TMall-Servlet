package com.amos.service;

import com.amos.bean.ProductImage;
import com.amos.dao.ProductImageDao;
import com.amos.dao.impl.ProductImageDaoImpl;

import java.util.List;

/**
 * @author Amos
 * @date 8/7/2020 9:16 PM
 */
public class ProductImageService {
    public static class ImageType {
        public static final String TYPE_TOP = "type_top";
        public static final String TYPE_DETAIL = "type_detail";
    }
    private ProductImageDao dao = new ProductImageDaoImpl();
    public void add(ProductImage productImage) {
        dao.add(productImage);
    }
    public void update(ProductImage productImage) {
        dao.update(productImage);
    }
    public void delete(int id) {
        dao.delete(id);
    }
    public ProductImage get(int id){
        return dao.get(id);
    }

    public ProductImage getFirstImage(int pid){
        ProductImage productImage= null;
        List<ProductImage> productImageList= dao.list(pid,ImageType.TYPE_TOP,0,1);
        if(productImageList.size()>0){
            productImage = productImageList.get(0);
        }
        return productImage;
    }

    public List<ProductImage> listTopImage(int pid, int start , int count){
        return dao.list(pid,ImageType.TYPE_TOP,start,count);
    }
    public List<ProductImage> listDetailImage(int pid, int start , int count){
        return dao.list(pid,ImageType.TYPE_DETAIL,start,count);
    }
}

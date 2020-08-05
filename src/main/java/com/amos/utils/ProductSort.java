package com.amos.utils;
import java.util.Comparator;
import java.util.List;
import com.amos.bean.Product;
/**
 * @author Amos
 * @date 8/5/2020 7:17 PM
 * 产品排序
 */
public class ProductSort {
    public static void sort(List<Product> products,String sortWay){
        sortWay = sortWay == null?"default":sortWay;
        //利用JDK8的新特性Lambda表达式
        switch (sortWay){
            case "comment":
                products.sort((p1, p2) -> p2.getCommentCount() - p1.getCommentCount());
                break;
            case "date":
                products.sort(Comparator.comparing(Product::getCreateDate));
                break;
            case "saleCount":
                products.sort((p1,p2)->p2.getCommentCount()-p1.getCommentCount());
                break;
            case "price":
                products.sort(Comparator.comparing(Product::getNowPrice));
                break;
            case "priceInverse":
                products.sort((p1,p2)-> p2.getNowPrice().compareTo(p1.getNowPrice()));
                break;
            default:
                products.sort((p1,p2)->p2.getCommentCount()*p2.getSaleCount()-p1.getCommentCount()-p1.getSaleCount());
                break;
        }
    }

}

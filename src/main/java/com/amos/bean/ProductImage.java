package com.amos.bean;

/**
 * @author Amos
 * @date 8/5/2020 7:25 PM
 */
public class ProductImage {
    private String type;
    private Product product;
    private int id;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

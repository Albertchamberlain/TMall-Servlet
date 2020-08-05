package com.amos.bean;

import java.math.BigDecimal;

/**
 * @author Amos
 * @date 8/5/2020 7:24 PM
 */
public class OrderItem {
    private int id;
    private Product product;
    private Order order;
    private int number;
    private BigDecimal sum;

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}

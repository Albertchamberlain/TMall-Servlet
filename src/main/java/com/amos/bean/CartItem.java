package com.amos.bean;
import java.math.BigDecimal;

/**
 * @author Amos
 * @date 8/5/2020 7:20 PM
 */
public class CartItem {
    private int id;
    private Product product;
    private User user;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

}

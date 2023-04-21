package edu.hanu.mycart.models;

import java.util.List;
import java.util.Objects;

public class CartItem {
    private Long cartId;
    private Long productId;
    private Product product;
    private int quantity;
    private int totalPrice;


    public CartItem(Long cartId, Product product, int quantity) {
        this.cartId = cartId;
        this.product = product;
        this.quantity = quantity;
        this.totalPrice = product.getUnitPrice()*quantity;
    }

    public Long getCartId() {
        return cartId;
    }

    public Product getProduct() {
        return product;
    }

    public Long getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        this.totalPrice = quantity * this.product.getUnitPrice();
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}

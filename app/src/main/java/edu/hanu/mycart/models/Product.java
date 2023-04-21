package edu.hanu.mycart.models;

import androidx.annotation.NonNull;

public class Product {
    @NonNull
    private Long id;
    @NonNull
    private String name;
    private String category;
    @NonNull
    private int unitPrice;
    @NonNull
    private String thumbnail;

    private boolean isAdd;

    public Product(@NonNull Long id, String name, String category, int unitPrice, String thumbnail) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.unitPrice = unitPrice;
        this.thumbnail = thumbnail;
        this.isAdd = false;
    }

    @NonNull
    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(@NonNull String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getCategory() {
        return category;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }

    public void setAdd(boolean b) {
        this.isAdd = b;
    }

    public boolean isAdd() {
        return this.isAdd;
    }

    @NonNull
    @Override
    public String toString() {
        return "id: "+getId()+"/ name: "+getName()+"/ price: $"+getUnitPrice();
    }
}

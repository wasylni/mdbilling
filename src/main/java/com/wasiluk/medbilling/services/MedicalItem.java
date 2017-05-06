package com.wasiluk.medbilling.services;

import java.math.BigDecimal;

/**
 * Created by marcin on 05-May-17.
 */
public class MedicalItem {
    private String name;

    private int quantity;

    private BigDecimal price;

    public MedicalItem(String name, BigDecimal price) {
        setName(name);
        setPrice(price);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}

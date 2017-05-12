package com.wasiluk.medbilling.treatment;

import java.math.BigDecimal;

/**
 * Created by marcin on 13-May-17.
 */
public class Treatment {

    private TreatmentEnum name;

    private int quantity;

    private BigDecimal price;

    public Treatment(TreatmentEnum name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    public Treatment() {
    }

    public TreatmentEnum getName() {
        return name;
    }

    public void setName(TreatmentEnum name) {
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

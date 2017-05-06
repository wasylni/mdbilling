package com.wasiluk.medbilling.basket;

import com.google.common.collect.Maps;
import com.wasiluk.medbilling.basket.patient.Patient;
import com.wasiluk.medbilling.services.MedicalItem;

import java.util.Map;

/**
 * Created by marcin on 05-May-17.
 */
public class Basket {

    private Map<MedicalItem, Integer> items = Maps.newConcurrentMap();

    private Patient patient;

    private double discount = 0;

    private Map<MedicalItem, Double> itemDiscount = Maps.newConcurrentMap();

    public void addItemDiscount(MedicalItem item, Double discount) {
        this.itemDiscount.put(item, discount);
    }

    public Map<MedicalItem, Integer> getItems() {
        return items;
    }

    public void setItems(Map<MedicalItem, Integer> items) {
        this.items = items;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public Map<MedicalItem, Double> getItemDiscount() {
        return itemDiscount;
    }

    public void setItemDiscount(Map<MedicalItem, Double> itemDiscount) {
        this.itemDiscount = itemDiscount;
    }

    public void addItem(MedicalItem item, int quantity) {
        items.put(item, quantity);
    }
}

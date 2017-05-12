package com.wasiluk.medbilling.bill;

import com.google.common.collect.Maps;
import com.wasiluk.medbilling.discount.Discount;
import com.wasiluk.medbilling.patient.Patient;
import com.wasiluk.medbilling.treatment.Treatment;

import java.util.Map;
import java.util.Set;

/**
 * Created by marcin on 13-May-17.
 */
public class Bill {

    private Patient patient;

    private Map<Treatment, Integer> items = Maps.newConcurrentMap();

    private Set<Discount> discount;


    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Map<Treatment, Integer> getItems() {
        return items;
    }

    public void setItems(Map<Treatment, Integer> items) {
        this.items = items;
    }

    public Set<Discount> getDiscount() {
        return discount;
    }

    public void setDiscount(Set<Discount> discount) {
        this.discount = discount;
    }

    public void addDiscount(Discount discount) {
        this.discount.add(discount);
    }
}

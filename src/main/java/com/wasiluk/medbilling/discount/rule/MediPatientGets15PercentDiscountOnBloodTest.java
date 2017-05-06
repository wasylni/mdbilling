package com.wasiluk.medbilling.discount.rule;


import com.wasiluk.medbilling.basket.Basket;
import com.wasiluk.medbilling.discount.DiscountRule;

/**
 * Created by marcin on 06-May-17.
 */
public class MediPatientGets15PercentDiscountOnBloodTest implements DiscountRule {

    @Override
    public double calculateSingleRuleDiscount(Basket basket) {
        if (basket.getPatient().isMediHealth() && basket.getItems().keySet().stream().anyMatch(item -> item.getName().equals("Blood Test"))) {
            basket.addItemDiscount(basket.getItems().keySet().stream().filter(i -> i.getName().equals("Blood Test")).findFirst().get(), 0.15);
        }
        return 0;
    }

}

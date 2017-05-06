package com.wasiluk.medbilling.discount.rule;


import com.wasiluk.medbilling.basket.Basket;
import com.wasiluk.medbilling.discount.DiscountRule;

/**
 * Created by marcin on 06-May-17.
 */
public class AgeOver70gets90PercentDiscount implements DiscountRule {

    @Override
    public double calculateSingleRuleDiscount(Basket basket) {
        if (  basket.getPatient().getAge() >70) {
            return 0.9;
        }
        return 0;
    }

}

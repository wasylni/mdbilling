package com.wasiluk.medbilling.discount;

import com.google.common.collect.Sets;
import com.wasiluk.medbilling.basket.Basket;
import com.wasiluk.medbilling.discount.rule.AgeBetween65and70gets60PercentDiscount;
import com.wasiluk.medbilling.discount.rule.AgeLessThan5gets40PercentDiscount;
import com.wasiluk.medbilling.discount.rule.AgeOver70gets90PercentDiscount;
import com.wasiluk.medbilling.discount.rule.MediPatientGets15PercentDiscountOnBloodTest;

import java.util.Set;

/**
 * Created by marcin on 06-May-17.
 */
public class DiscountRuleServiceImpl implements DiscountRuleService {

    Set<DiscountRule> discountRules = Sets.newConcurrentHashSet();

    public DiscountRuleServiceImpl()
    {
        discountRules.add(new AgeBetween65and70gets60PercentDiscount());
        discountRules.add(new AgeOver70gets90PercentDiscount());
        discountRules.add(new AgeLessThan5gets40PercentDiscount());
        discountRules.add(new MediPatientGets15PercentDiscountOnBloodTest());
    }

    @Override
    public double calculateDiscount(Basket basket) {
        final double[] discount = {0};
        discountRules.forEach(rule -> discount[0] = Math.max(discount[0], rule.calculateSingleRuleDiscount(basket)));
        basket.setDiscount(discount[0]);
        return discount[0];
    }
}

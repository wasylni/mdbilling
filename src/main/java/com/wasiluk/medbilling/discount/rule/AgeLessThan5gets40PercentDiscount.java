package com.wasiluk.medbilling.discount.rule;

import com.wasiluk.medbilling.discount.DiscountRuleService;
import com.wasiluk.medbilling.discount.Discount;
import com.wasiluk.medbilling.bill.Bill;

/**
 * Created by marcin on 13-May-17.
 */
public class AgeLessThan5gets40PercentDiscount implements DiscountRuleService {

    @Override
    public Discount getApplicableDiscount(Bill treatment) {
        if (  treatment.getPatient().getAge() < 5) {
            return new Discount(0.4);
        }
        return new Discount();
    }


}

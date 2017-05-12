package com.wasiluk.medbilling.discount.rule;


import com.wasiluk.medbilling.discount.DiscountRuleService;
import com.wasiluk.medbilling.discount.Discount;
import com.wasiluk.medbilling.bill.Bill;

/**
 * Created by marcin on 13-May-17.
 */
public class AgeBetween65and70gets60PercentDiscount implements DiscountRuleService {

    @Override
    public Discount getApplicableDiscount(Bill treatment) {
        if (treatment.getPatient().getAge() >= 65 && treatment.getPatient().getAge() <= 70) {
            return new Discount(0.6);
        }
        return new Discount();
    }


}

package com.wasiluk.medbilling.discount;

import com.google.common.collect.Sets;
import com.wasiluk.medbilling.bill.Bill;
import com.wasiluk.medbilling.discount.rule.AgeBetween65and70gets60PercentDiscount;
import com.wasiluk.medbilling.discount.rule.AgeLessThan5gets40PercentDiscount;
import com.wasiluk.medbilling.discount.rule.AgeOver70gets90PercentDiscount;
import com.wasiluk.medbilling.discount.rule.MediPatientGets15PercentDiscountOnBloodTest;
import com.wasiluk.medbilling.treatment.TreatmentEnum;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by marcin on 13-May-17.
 */
public class DiscountRuleServiceImpl implements DiscountService {

    private static Set<DiscountRuleService> discountRule;

    DiscountRuleServiceImpl() {
        if (discountRule == null) {
            discountRule = Sets.newConcurrentHashSet();
            discountRule.add(new AgeBetween65and70gets60PercentDiscount());
            discountRule.add(new AgeOver70gets90PercentDiscount());
            discountRule.add(new AgeLessThan5gets40PercentDiscount());
            discountRule.add(new MediPatientGets15PercentDiscountOnBloodTest());
        }
    }

    @Override
    public Set<Discount> setBillDiscount(Bill bill) {
        Set<Discount> discounts = Sets.newHashSet();
        discountRule.forEach(rule -> {
            discounts.add(addDiscount(bill, rule));
        });
        return discounts;
    }

    Discount addDiscount(Bill bill, DiscountRuleService rule) {
        final Discount discount = rule.getApplicableDiscount(bill);
        if (discount.getPercent() != 0) {
            bill.addDiscount(discount);
            return discount;
        }
        return new Discount(0);
    }


    @Override
    public double getGlobalDiscount(Bill bill) {
        return setBillDiscount(bill).stream().map(discount -> discount.getPercent()).collect(Collectors.toList()).stream().mapToDouble(Double::doubleValue).sum();
    }

    @Override
    public double getItemDiscount(Bill bill, TreatmentEnum treatmentEnum) {
        return setBillDiscount(bill).stream().filter(itemDiscount -> itemDiscount.getTreatment()== treatmentEnum)
                .collect(Collectors.toSet()).stream().map(discount -> discount.getPercent()).collect(Collectors.toList()).stream().mapToDouble(Double::doubleValue).sum();
    }

}

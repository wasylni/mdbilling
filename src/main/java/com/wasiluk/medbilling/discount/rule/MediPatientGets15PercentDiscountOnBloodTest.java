package com.wasiluk.medbilling.discount.rule;


import com.wasiluk.medbilling.bill.Bill;
import com.wasiluk.medbilling.discount.Discount;
import com.wasiluk.medbilling.discount.DiscountRuleService;
import com.wasiluk.medbilling.treatment.TreatmentEnum;

/**
 * Created by marcin on 13-May-17.
 */
public class MediPatientGets15PercentDiscountOnBloodTest implements DiscountRuleService {

    @Override
    public Discount getApplicableDiscount(Bill treatment) {
        if (treatment.getPatient().isMediHealth() && treatment.getItems().keySet().stream().anyMatch(item -> item.getName().equals(TreatmentEnum.BLOODTEST))) {
            return new Discount(treatment.getItems().keySet().stream().filter(i -> i.getName().equals(TreatmentEnum.BLOODTEST)).findFirst().get().getName(), 0.15);
        }
        return new Discount();
    }

}

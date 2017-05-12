package com.wasiluk.medbilling.bill;

import com.wasiluk.medbilling.discount.Discount;
import com.wasiluk.medbilling.treatment.Treatment;
import com.wasiluk.medbilling.treatment.TreatmentEnum;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by marcin on 13-May-17.
 */
public class BillingServiceImpl implements BillingService {


    @Override
    public BigDecimal calculatePrice(Bill bill) {
        BigDecimal treatmentTotalPrice = calculateTotal(bill);
        BigDecimal globalDiscount = getGlobalDiscount(bill);
        return (globalDiscount.compareTo(BigDecimal.ZERO) != 0) ? treatmentTotalPrice.subtract(globalDiscount) : treatmentTotalPrice.subtract(getPerTreatmentSumDiscount(bill));
    }

    @Override
    public BigDecimal getGlobalDiscount(Bill bill) {
        BigDecimal treatmentTotalPrice = calculateTotal(bill);
        double globalDiscount = bill.getDiscount().stream().filter(discount -> discount.getPercent() > 0 && discount.getTreatment()== null).findAny().get().getPercent();
        return treatmentTotalPrice.multiply(BigDecimal.valueOf(globalDiscount));
    }

    @Override
    public BigDecimal getPerTreatmentSumDiscount(Bill bill) {
        BigDecimal totalItemDiscount = BigDecimal.ZERO;
        Set<Discount> itemDiscounts = bill.getDiscount().stream().filter(discount -> discount.getTreatment() != null).collect(Collectors.toSet());
        for (Discount dsc : itemDiscounts) {
               totalItemDiscount = totalItemDiscount.add(BigDecimal.valueOf(dsc.getPercent()).multiply(calculateTotalPerItem(bill, dsc.getTreatment())));
        }
        return totalItemDiscount;
    }

    BigDecimal calculateTotal(Bill bill) {
        return (bill!=null && !bill.getItems().isEmpty())? bill.getItems().entrySet().stream().map(entry -> entry.getKey().getPrice().multiply(BigDecimal.valueOf(entry.getValue()))).reduce(BigDecimal::add).get(): BigDecimal.ZERO;
    }

    BigDecimal calculateTotalPerItem(Bill bill, TreatmentEnum treatmentEnum) {
        for (Map.Entry<Treatment, Integer> entry : bill.getItems().entrySet()) {
            if (entry.getKey() != null && entry.getKey().getName().equals(treatmentEnum)) {
                return entry.getKey().getPrice().multiply(BigDecimal.valueOf(entry.getValue()));
            }
        }
        return BigDecimal.ZERO;
    }




}

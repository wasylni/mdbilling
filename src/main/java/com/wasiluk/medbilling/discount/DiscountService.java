package com.wasiluk.medbilling.discount;

import com.wasiluk.medbilling.bill.Bill;
import com.wasiluk.medbilling.treatment.TreatmentEnum;

import java.util.Set;

/**
 * Created by marcin on 13-May-17.
 */
public interface DiscountService {

    Set<Discount> setBillDiscount(Bill bill);

    double getGlobalDiscount(Bill bill);

    double getItemDiscount(Bill bill, TreatmentEnum treatmentEnum);
}

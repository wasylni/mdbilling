package com.wasiluk.medbilling.bill;

import java.math.BigDecimal;

/**
 * Created by marcin on 13-May-17.
 */
public interface BillingService {

    BigDecimal calculatePrice(Bill bill);

    BigDecimal getGlobalDiscount(Bill bill);

    BigDecimal getPerTreatmentSumDiscount(Bill bill);

}

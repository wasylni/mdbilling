package com.wasiluk.medbilling.discount;


import com.wasiluk.medbilling.bill.Bill;

/**
 * Created by marcin on 13-May-17.
 */
public interface DiscountRuleService {

     Discount getApplicableDiscount(Bill treatment);

}

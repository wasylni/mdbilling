package com.wasiluk.medbilling.discount;


import com.wasiluk.medbilling.basket.Basket;

/**
 * Created by marcin on 06-May-17.
 */
public interface DiscountRuleService {

     double calculateDiscount(Basket basket);

}

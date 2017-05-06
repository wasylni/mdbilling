package com.wasiluk.medbilling.discount;


import com.wasiluk.medbilling.basket.Basket;

/**
 * Created by marcin on 06-May-17.
 */
public interface DiscountRule {

    double calculateSingleRuleDiscount(Basket basket);

}

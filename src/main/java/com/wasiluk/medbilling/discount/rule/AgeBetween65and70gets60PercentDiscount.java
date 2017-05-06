package com.wasiluk.medbilling.discount.rule;


import com.wasiluk.medbilling.basket.Basket;
import com.wasiluk.medbilling.discount.DiscountRule;

/**
 * Created by marcin on 06-May-17.
 */
public class AgeBetween65and70gets60PercentDiscount implements DiscountRule {

    @Override
    public double calculateSingleRuleDiscount(Basket basket) {
          if (  basket.getPatient().getAge() >=65 && basket.getPatient().getAge() <=70) {
              return 0.6;
          }
        return 0;
    }


}

package com.wasiluk.medbilling.basket;

import java.math.BigDecimal;

/**
 * Created by marcin on 05-May-17.
 */
public class BasketServiceImpl implements BasketService {

    @Override
    public BigDecimal calculatePrice(Basket basket) {
        final BigDecimal basketTotalPrice = calculateDiscount(basket);
        if (haveGlobalDiscount(basket)) {
            return basketTotalPrice.subtract(BigDecimal.valueOf(basket.getDiscount()).multiply(basketTotalPrice));
        } else if (haveItemDiscount(basket)) {
            return basketTotalPrice.subtract(calculateItemizedDiscount(basket));
        } else {
            return basketTotalPrice;
        }
    }

    private BigDecimal calculateDiscount(Basket basket) {
        final BigDecimal[] basketTotalPrice = {BigDecimal.ZERO};
        basket.getItems().forEach((item, quantity) -> basketTotalPrice[0] = basketTotalPrice[0].add(item.getPrice().multiply(BigDecimal.valueOf(quantity))));
        return basketTotalPrice[0];
    }

    private BigDecimal calculateItemizedDiscount(Basket basket) {
        final BigDecimal[] totalDiscountPerItem = {BigDecimal.ZERO};
        basket.getItemDiscount().forEach((key, value) -> {
            final Integer quantity = basket.getItems().get(key);
            if (basket.getItems().get(key) != null) {
                totalDiscountPerItem[0] = totalDiscountPerItem[0].add(BigDecimal.valueOf(value).multiply(BigDecimal.valueOf(quantity).multiply(key.getPrice())));
            }
        });
        return totalDiscountPerItem[0];
    }

    private boolean haveItemDiscount(Basket basket) {
        return !basket.getItemDiscount().isEmpty();
    }

    private boolean haveGlobalDiscount(Basket basket) {
        return basket.getDiscount() > 0;
    }

}

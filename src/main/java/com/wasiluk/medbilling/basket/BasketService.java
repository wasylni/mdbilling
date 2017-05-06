package com.wasiluk.medbilling.basket;

import java.math.BigDecimal;

/**
 * Created by marcin on 05-May-17.
 */
public interface BasketService {

    BigDecimal calculatePrice(Basket basket);

}

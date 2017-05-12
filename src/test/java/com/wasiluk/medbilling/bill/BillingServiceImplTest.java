package com.wasiluk.medbilling.bill;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.wasiluk.medbilling.discount.Discount;
import com.wasiluk.medbilling.treatment.Treatment;
import com.wasiluk.medbilling.treatment.TreatmentEnum;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Created by marcin.
 */
@RunWith(MockitoJUnitRunner.class)
public class BillingServiceImplTest {

    @Mock
    BillingServiceImpl billingService;

    @Mock
    Bill bill;

    private static final double DELTA = 1e-15;

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void getGlobalDiscount() throws Exception {
        Set<Discount> itemDiscounts = Sets.newHashSet();
        itemDiscounts.add(new Discount(null, 0.1));

        when(billingService.getGlobalDiscount(bill)).thenCallRealMethod();
        when(billingService.calculateTotal(bill)).thenReturn(new BigDecimal(9999.99));
        when(bill.getDiscount()).thenReturn(itemDiscounts);

        assertEquals("10% from 9999.99 is 999.999 ", BigDecimal.valueOf(999.999).doubleValue(), billingService.getGlobalDiscount(bill).doubleValue(), DELTA);
    }


    @Test
    public void calculateTotal() throws Exception {
        Map<Treatment, Integer> items = Maps.newConcurrentMap();
        Treatment treatment = new Treatment(TreatmentEnum.VACCINE, BigDecimal.valueOf(27.50));
        Treatment vaccines = new Treatment(TreatmentEnum.VACCINE_ITEM, BigDecimal.valueOf(15));
        items.put(treatment, 1);
        items.put(vaccines, 2);

        when(billingService.calculateTotal(bill)).thenCallRealMethod();
        when(bill.getItems()).thenReturn(items);

        assertEquals("Total price should be (1 * 27.5) + (2 * 15) = 57.5", BigDecimal.valueOf(57.5), billingService.calculateTotal(bill));
    }

    @Test
    public void calculateTotalForEmptyBasket() throws Exception {
        Map<Treatment, Integer> items = Maps.newConcurrentMap();
        when(billingService.calculateTotal(bill)).thenCallRealMethod();
        when(bill.getItems()).thenReturn(items);

        assertEquals("Empty basket should return total of 0", BigDecimal.valueOf(0), billingService.calculateTotal(bill));
    }


    @Test
    public void getPerTreatmentSumDiscount() throws Exception {
        Map<Treatment, Integer> items = Maps.newConcurrentMap();
        Treatment treatment = new Treatment(TreatmentEnum.X_RAY, BigDecimal.valueOf(60));
        Treatment vaccines = new Treatment(TreatmentEnum.VACCINE_ITEM, BigDecimal.valueOf(15));
        items.put(treatment, 1);
        items.put(vaccines, 2);

        Set<Discount> itemDiscounts = Sets.newHashSet();
        itemDiscounts.add(new Discount(TreatmentEnum.X_RAY, 0.25));
        itemDiscounts.add(new Discount(null, 0.80));

        when(bill.getDiscount()).thenReturn(itemDiscounts);
        when(bill.getItems()).thenReturn(items);
        when(billingService.getPerTreatmentSumDiscount(bill)).thenCallRealMethod();
        when(billingService.calculateTotalPerItem(bill, TreatmentEnum.X_RAY)).thenCallRealMethod();

        assertEquals("discount for X_RAY is 25% therefore 60 * 0.25 = 15 ", BigDecimal.valueOf(15).doubleValue(), billingService.getPerTreatmentSumDiscount(bill).doubleValue(), DELTA);
    }


    @Test
    public void calculatePrice() {
        when(billingService.calculatePrice(bill)).thenCallRealMethod();
        when(billingService.calculateTotal(bill)).thenReturn(BigDecimal.valueOf(72.5));
        when(billingService.getGlobalDiscount(bill)).thenReturn(BigDecimal.ZERO);
        when(billingService.getPerTreatmentSumDiscount(bill)).thenReturn(BigDecimal.ZERO);

        BigDecimal result = billingService.calculatePrice(bill);
        assertEquals("Price for vaccination with 3 vaccines should be equal to 27.5 + (3 * 15) = 72.5", BigDecimal.valueOf(72.5), result);
    }

}
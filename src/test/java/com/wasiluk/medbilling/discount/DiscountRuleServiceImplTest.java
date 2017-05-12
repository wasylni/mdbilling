package com.wasiluk.medbilling.discount;

import com.google.common.collect.Maps;
import com.wasiluk.medbilling.bill.Bill;
import com.wasiluk.medbilling.patient.Patient;
import com.wasiluk.medbilling.treatment.Treatment;
import com.wasiluk.medbilling.treatment.TreatmentEnum;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Created by marcin on 13-May-17.
 */
@RunWith(MockitoJUnitRunner.class)
public class DiscountRuleServiceImplTest {

    DiscountRuleServiceImpl discountService;

    @Mock
    Bill bill;

    @Mock
    Patient patient;

    private static final double DELTA = 1e-15;

    @Before
    public void setUp() throws Exception {
        discountService = new DiscountRuleServiceImpl();

    }

    @Test
    public void checkDiscount65to70() {
        when(bill.getPatient()).thenReturn(patient);
        when(patient.getAge()).thenReturn(65);
        assertEquals("Senior citizens (between 65 and 70) receive a 60% discount", 0.6, discountService.getGlobalDiscount(bill) , DELTA);
    }

    @Test
    public void checkDiscountOver70() {
        when(bill.getPatient()).thenReturn(patient);
        when(patient.getAge()).thenReturn(71);
        assertEquals("Senior citizens (over 70) receive a 90% discount", 0.9, discountService.getGlobalDiscount(bill), DELTA);
        }

    @Test
    public void checkDiscountChildUnder5() {
        when(bill.getPatient()).thenReturn(patient);
        when(patient.getAge()).thenReturn(4);
        assertEquals("Children under 5 receive 40% discount", 0.4, discountService.getGlobalDiscount(bill), DELTA);
    }

    @Test
    public void checkDiscountMediHealthPatientBloodDiscount() {
        Map<Treatment, Integer> items = Maps.newConcurrentMap();
        Treatment treatment = new Treatment(TreatmentEnum.BLOODTEST, BigDecimal.valueOf(60));
        items.put(treatment, 10);

        when(bill.getPatient()).thenReturn(patient);
        when(patient.getAge()).thenReturn(35);
        when(patient.isMediHealth()).thenReturn(true);
        when(bill.getItems()).thenReturn(items);

        assertEquals("Patients with \"MediHealth Health insurance\" get additional 15% discount on Blood test when they are diagnosed by a MediHealth practitioner", 0.15, discountService.getItemDiscount(bill, TreatmentEnum.BLOODTEST), DELTA);
     }



}
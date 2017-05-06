import com.wasiluk.medbilling.basket.Basket;
import com.wasiluk.medbilling.basket.BasketService;
import com.wasiluk.medbilling.basket.BasketServiceImpl;
import com.wasiluk.medbilling.basket.patient.Patient;
import com.wasiluk.medbilling.discount.DiscountRuleService;
import com.wasiluk.medbilling.discount.DiscountRuleServiceImpl;
import com.wasiluk.medbilling.services.MedicalItem;
import com.wasiluk.medbilling.services.ServiceItemsService;
import com.wasiluk.medbilling.services.ServiceItemsServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by marcin on 04-May-17.
 */
public class ServiceItemsTest {

    private ServiceItemsService serviceItemsService = new ServiceItemsServiceImpl();
    private BasketService basketService = new BasketServiceImpl();
    private Set<String> serviceItemsCompare = new HashSet<>();
    private DiscountRuleService discountRuleService = new DiscountRuleServiceImpl();

    private Basket basket;


    @Before
    public void setUp() throws Exception {
        serviceItemsService.addService("Diagnosis", BigDecimal.valueOf(60.00));
        serviceItemsService.addService("X-Ray", BigDecimal.valueOf(150.00));
        serviceItemsService.addService("Blood Test", BigDecimal.valueOf(78.00));
        serviceItemsService.addService("ECG", BigDecimal.valueOf(200.40));
        serviceItemsService.addService("Vaccine", BigDecimal.valueOf(27.50));
        serviceItemsService.addService("Vaccine Item", BigDecimal.valueOf(15.00));

        serviceItemsCompare.add("Diagnosis");
        serviceItemsCompare.add("X-Ray");
        serviceItemsCompare.add("Blood Test");
        serviceItemsCompare.add("ECG");
        serviceItemsCompare.add("Vaccine");

        MedicalItem vaccine = serviceItemsService.getService("Vaccine").get();
        MedicalItem vaccineI = serviceItemsService.getService("Vaccine Item").get();


        basket = new Basket();
        basket.addItem(vaccine, 1);
        basket.addItem(vaccineI, 3);
    }

    /**
     * MediHealth is a private medical centre. They provide following services:
     * 1. Diagnosis
     * 2. X-Ray
     * 3. Blood Test
     * 4. ECG
     * 5. Vaccine
     */
    @Test
    public void checkServiceItems() {
        boolean allFound = serviceItemsCompare.stream().anyMatch((String service) -> !serviceItemsService.getService(service).isPresent());
        Assert.assertFalse("Check that all service items are present", allFound);
    }


    /**
     * Each of the services has an associated default cost
     * 1. Diagnosis £60
     * 2. X-Ray £150
     * 3. Blood Test £78
     * 4. ECG £200.40
     * 5. Vaccine £27.50 (service) + £15 (for each vaccine)
     **/
    @Test
    public void checkServicePricing() {
        BigDecimal result = basketService.calculatePrice(basket);
        Assert.assertTrue("Price for vaccination with 3 vaccines should be equal to 27.5 + (3 * 15) = 72.5", BigDecimal.valueOf(72.5).compareTo(result) == 0);
    }


    /**
     * 1. Senior citizens (between 65 and 70) receive a 60% discount
     */
    @Test
    public void checkDiscount65to70() {
        basket.setPatient(new Patient(65));
        discountRuleService.calculateDiscount(basket);
        Assert.assertTrue("Senior citizens (between 65 and 70) receive a 60% discount", Double.compare(0.6, basket.getDiscount()) == 0);
        Assert.assertTrue("Price for vaccination with 3 vaccines should be equal to 27.5 + (3 * 15) = 72.5 less 60% discount equals 72.5 - 43.5 = 29", BigDecimal.valueOf(29.00).compareTo(basketService.calculatePrice(basket)) == 0);
    }

    /**
     * 2. Senior citizens (over 70) receive a 90% discount
     */
    @Test
    public void checkDiscountOver70() {
        basket.setPatient(new Patient(71));
        discountRuleService.calculateDiscount(basket);
        Assert.assertTrue("Senior citizens (over 70) receive a 90% discount", Double.compare(0.9, basket.getDiscount()) == 0);
        Assert.assertTrue("Price for vaccination with 3 vaccines should be equal to 27.5 + (3 * 15) = 72.5 less 90% discount equals 72.5 - 65.25 = 7.25", BigDecimal.valueOf(7.25).compareTo(basketService.calculatePrice(basket)) == 0);
    }

    /**
     * 3. Children under 5 receive 40% discount
     */
    @Test
    public void checkDiscountChildUnder5() {
        basket.setPatient(new Patient(4));
        discountRuleService.calculateDiscount(basket);
        Assert.assertTrue("Children under 5 receive 40% discount", Double.compare(0.4, basket.getDiscount()) == 0);
        Assert.assertTrue("Price for vaccination with 3 vaccines should be equal to 27.5 + (3 * 15) = 72.5 less 40% discount equals 72.5 - 29 = 43.5", BigDecimal.valueOf(43.5).compareTo(basketService.calculatePrice(basket)) == 0);
    }


    /**
     * 4. Patients with "MediHealth Health insurance" get additional 15% discount on Blood test when they are diagnosed by a MediHealth practitioner
     */
    @Test
    public void checkDiscountMediHealthPatientBloodDiscount() {
        Patient patient = new Patient(35);
        patient.setMediHealth(true);

        basket.setPatient(patient);
        basket.addItem(serviceItemsService.getService("Blood Test").get(), 1);
        discountRuleService.calculateDiscount(basket);
        Map<MedicalItem, Double> bloodDiscount = basket.getItemDiscount().entrySet().stream().filter(dscItem -> dscItem.getKey().getName().equals("Blood Test")).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        Assert.assertTrue("Patients with \"MediHealth Health insurance\" get additional 15% discount on Blood test when they are diagnosed by a MediHealth practitioner", basket.getDiscount() == 0 && bloodDiscount.size() == 1 && bloodDiscount.get(serviceItemsService.getService("Blood Test").get()).equals(0.15));
        Assert.assertTrue("vaccination with 3 vaccines equal to 27.5 + (3 * 15) = 72.5 plus Blood test 78 = 150.5  less 15% discount on blod test 150.5 - 11.7  = 138.8", BigDecimal.valueOf(138.800).compareTo(basketService.calculatePrice(basket)) == 0);
    }

}
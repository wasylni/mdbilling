package com.wasiluk.medbilling.services;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Created by marcin on 04-May-17.
 */
public interface ServiceItemsService {

    Optional<MedicalItem> getService(String service);

    void addService(String service, BigDecimal price);
}

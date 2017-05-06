package com.wasiluk.medbilling.services;

import com.google.common.collect.Sets;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * Created by marcin on 04-May-17.
 */
public class ServiceItemsServiceImpl implements ServiceItemsService {

    private static Set<MedicalItem> serviceItemsAvailable;

    public ServiceItemsServiceImpl() {
        if (serviceItemsAvailable == null) {
            serviceItemsAvailable = Sets.newConcurrentHashSet();
        }
    }

    @Override
    public Optional<MedicalItem> getService(String service) {
        return serviceItemsAvailable.stream().filter(item -> Objects.equals(item.getName(), service)).findFirst();
    }

    @Override
    public void addService(String service, BigDecimal price) {
        serviceItemsAvailable.add(new MedicalItem(service, price) {
        });
    }
}

package com.epam.esm.service.certificate.update.property.event;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class PriceChangeEventListener implements ApplicationListener<PriceChangeEvent> {
    @Override
    public void onApplicationEvent(PriceChangeEvent event) {
        System.out.println("Received spring custom event - " + event.getMessage());
    }
}

package com.epam.esm.service.certificate.update.property;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.service.certificate.update.property.event.PriceChangeEvent;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@AllArgsConstructor
@Component
public class UpdateCertificatePrice implements UpdateProperty<GiftCertificate, BigDecimal> {
    private ApplicationEventPublisher eventPublisher;

    public boolean update(GiftCertificate certificate, BigDecimal price){
        if (price == null || certificate.getPrice().equals(price)) return false;
        PriceChangeEvent event = new PriceChangeEvent(this, certificate.getId(), certificate.getPrice(), price);
        certificate.setPrice(price);
        eventPublisher.publishEvent(event);
        return true;
    }
}

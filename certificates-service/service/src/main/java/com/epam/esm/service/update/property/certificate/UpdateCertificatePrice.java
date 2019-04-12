package com.epam.esm.service.update.property.certificate;

import com.epam.esm.entity.GiftCertificate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class UpdateCertificatePrice implements UpdateProperty<GiftCertificate, BigDecimal> {
    public boolean update(GiftCertificate certificate, BigDecimal price){
        if (price == null || certificate.getPrice().equals(price)) return false;
        certificate.setPrice(price);
        System.out.println("Sending spam with new price...");
        return true;
    }
}

package com.epam.esm.service.certificate.update.property;

import com.epam.esm.entity.GiftCertificate;
import org.springframework.stereotype.Component;

@Component
public class UpdateCertificateDescription implements UpdateProperty<GiftCertificate, String> {
    public boolean update(GiftCertificate certificate, String description){
        if (description == null || certificate.getDescription().equals(description)) return false;
        certificate.setDescription(description);
        return false;
    }
}

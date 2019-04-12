package com.epam.esm.service.update.property.certificate;

import com.epam.esm.entity.GiftCertificate;
import org.springframework.stereotype.Component;

@Component
public class UpdateCertificateDuration implements UpdateProperty<GiftCertificate, Short> {
    public boolean update(GiftCertificate certificate, Short duration){
        if(duration == null || certificate.getDuration().equals(duration)) return false;
        certificate.setDuration(duration);
        return true;
    }
}

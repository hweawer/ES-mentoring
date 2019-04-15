package com.epam.esm.service.certificate.update.property;

import com.epam.esm.entity.GiftCertificate;
import org.springframework.stereotype.Component;

@Component
public class UpdateCertificateName implements UpdateProperty<GiftCertificate, String> {
    public boolean update(GiftCertificate certificate, String name){
        if (name == null || certificate.getName().equals(name)) return false;
        certificate.setName(name);
        return true;
    }
}

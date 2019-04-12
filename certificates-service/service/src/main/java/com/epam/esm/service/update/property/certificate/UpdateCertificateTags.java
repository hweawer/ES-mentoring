package com.epam.esm.service.update.property.certificate;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class UpdateCertificateTags implements UpdateProperty<GiftCertificate, Set<Tag>> {
    public boolean update(GiftCertificate certificate, Set<Tag> tags){
        if (tags == null || certificate.getTags().equals(tags)) return false;
        certificate.setTags(tags);
        return true;
    }
}

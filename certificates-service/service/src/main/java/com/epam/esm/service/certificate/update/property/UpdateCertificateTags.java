package com.epam.esm.service.certificate.update.property;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.repository.TagRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

@AllArgsConstructor
@Component
public class UpdateCertificateTags implements UpdateProperty<GiftCertificate, Set<Tag>> {
    public boolean update(GiftCertificate certificate, Set<Tag> tags){
        if (tags == null || certificate.getTags().equals(tags)) return false;
        certificate.setTags(tags);
        return true;
    }
}

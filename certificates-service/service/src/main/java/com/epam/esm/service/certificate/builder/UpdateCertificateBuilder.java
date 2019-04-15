package com.epam.esm.service.certificate.builder;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.certificate.update.property.*;
import com.epam.esm.service.exception.BuilderIsNotActive;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Set;

@RequiredArgsConstructor
@Component
public class UpdateCertificateBuilder {
    @NonNull
    private UpdateCertificateName updateName;
    @NonNull
    private UpdateCertificateDescription updateDescription;
    @NonNull
    private UpdateCertificatePrice updatePrice;
    @NonNull
    private UpdateCertificateDuration updateDuration;
    @NonNull
    private UpdateCertificateTags updateTags;

    @RequiredArgsConstructor
    public class InnerUpdateCertificateBuilder{
        @NonNull
        private GiftCertificate certificate;
        private boolean active = true;

        public InnerUpdateCertificateBuilder updateName(String name){
            if (!active) throw new BuilderIsNotActive("");
            updateName.update(certificate, name);
            return this;
        }

        public InnerUpdateCertificateBuilder updateDescription(String description){
            if (!active) throw new BuilderIsNotActive("");
            updateDescription.update(certificate, description);
            return this;
        }

        public InnerUpdateCertificateBuilder updatePrice(BigDecimal price){
            if (!active) throw new BuilderIsNotActive("");
            updatePrice.update(certificate, price);
            return this;
        }

        public InnerUpdateCertificateBuilder updateDuration(Short duration){
            if (!active) throw new BuilderIsNotActive("");
            updateDuration.update(certificate, duration);
            return this;
        }

        public InnerUpdateCertificateBuilder updateTags(Set<Tag> tagSet){
            if (!active) throw new BuilderIsNotActive("");
            updateTags.update(certificate, tagSet);
            return this;
        }

        public GiftCertificate build(){
            active = false;
            return certificate;
        }
    }

    public InnerUpdateCertificateBuilder update(GiftCertificate certificate){
        return this.new InnerUpdateCertificateBuilder(certificate);
    }

}

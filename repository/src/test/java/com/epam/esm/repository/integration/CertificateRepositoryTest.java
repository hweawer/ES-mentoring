package com.epam.esm.repository.integration;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.repository.config.RepositoryConfig;
import com.epam.esm.repository.integration.config.RepositoryTestConfig;
import com.epam.esm.repository.repository.Repository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {RepositoryConfig.class, RepositoryTestConfig.class})
@Transactional
@Sql(value = "classpath:init.sql")
@Rollback
public class CertificateRepositoryTest {
    @Autowired
    private Repository<GiftCertificate> certificateRepository;

    @Test
    public void certificateInsertTest(){
        GiftCertificate certificate = new GiftCertificate();
        certificate.setName("Test Certificate");
        certificate.setDescription("Test DESCRIPTION");
        certificate.setPrice(new BigDecimal(25.5));
        certificate.setDuration((short) 12);
        certificate.setCreationDate(LocalDate.of(2105, 12, 2));
        Tag certificateTag = new Tag();
        certificateTag.setId(5L);
        Set<Tag> tags = new HashSet<>();
        tags.add(certificateTag);
        certificate.setTags(tags);
        GiftCertificate inserted = certificateRepository.create(certificate);
        certificate.setId(inserted.getId());
        assertEquals(certificate, inserted);
    }

    @Test
    public void updateCertificate(){
        GiftCertificate selected = certificateRepository.findById(5L).get();
        selected.setDescription("New DESCRIPTION for test");
        certificateRepository.update(selected);
        GiftCertificate updated = certificateRepository.findById(5L).get();
        assertEquals(updated, selected);
    }

    @Test
    public void deleteCertificate(){
        int affectedRows = certificateRepository.delete(2L);
        assertEquals(1, affectedRows);
    }

    @Test
    public void deleteCertificateNegative(){
        int affectedRows = certificateRepository.delete(220L);
        assertEquals(0, affectedRows);
    }

}

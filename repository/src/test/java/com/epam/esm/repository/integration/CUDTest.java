package com.epam.esm.repository.integration;

import com.epam.esm.repository.RepositoryTestConfig;
import com.epam.esm.repository.config.CertificateTable;
import com.epam.esm.repository.config.RepositoryConfig;
import com.epam.esm.repository.config.TagTable;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.repository.repository.Repository;
import com.epam.esm.repository.repository.specification.SpecificationBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RepositoryConfig.class, RepositoryTestConfig.class})
@Transactional
@Sql(value = "classpath:init.sql")
@Rollback
public class CUDTest {
    @Autowired
    private Repository<Tag> tagRepository;
    @Autowired
    private Repository<GiftCertificate> certificateRepository;

    @Test
    public void tagInsertTest(){
        Tag inserted = tagRepository.create(new Tag("TestTag"));
        SpecificationBuilder builder = new SpecificationBuilder();
        builder.select().from(TagTable.tableName).where().equal(TagTable.id);
        Tag selected = tagRepository.queryFromDatabase(builder, new Object[]{inserted.getId()}).get(0);
        Assert.assertEquals(inserted, selected);
    }

    @Test
    public void certificateInsertTest(){
        SpecificationBuilder builder = new SpecificationBuilder();
        builder.select().from(TagTable.tableName);
        GiftCertificate certificate = new GiftCertificate();
        certificate.setName("Test Certificate");
        certificate.setDescription("Test description");
        certificate.setPrice(new BigDecimal(25.5));
        certificate.setDuration((short) 12);
        certificate.setCreationDate(LocalDate.of(2105, 12, 2));
        Tag certificateTag = new Tag();
        certificateTag.setId(5L);
        certificate.getTags().add(certificateTag);
        GiftCertificate inserted = certificateRepository.create(certificate);
        certificate.setId(inserted.getId());
        Assert.assertEquals(certificate, inserted);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void updateTag(){
        Tag inserted = tagRepository.create(new Tag("TestTag"));
        SpecificationBuilder builder = new SpecificationBuilder();
        builder.select().from(TagTable.tableName).where().equal(TagTable.id);
        Tag selected = tagRepository.queryFromDatabase(builder, new Object[]{inserted.getId()}).get(0);
        selected.setName("New name");
        tagRepository.update(selected);
    }

    @Test
    public void updateCertificate(){
        SpecificationBuilder builder = new SpecificationBuilder();
        builder.select().from(CertificateTable.tableName).where().equal(CertificateTable.id);
        GiftCertificate selected = certificateRepository.queryFromDatabase(builder, new Object[]{5L}).get(0);
        selected.setDescription("New description for test");
        certificateRepository.update(selected);
        GiftCertificate updated = certificateRepository.queryFromDatabase(builder, new Object[]{5L}).get(0);
        Assert.assertEquals(updated, selected);
    }

    @Test
    public void deleteTag(){
        Tag delete = new Tag();
        delete.setId(5L);
        int rows = tagRepository.remove(delete);
        Assert.assertEquals(1, rows);
    }

    @Test
    public void deleteTagNegative(){
        Tag delete = new Tag();
        delete.setId(16L);
        int rows = tagRepository.remove(delete);
        Assert.assertEquals(0, rows);
    }

    @Test
    public void deleteCertificate(){
        GiftCertificate delete = new GiftCertificate();
        delete.setId(2L);
        int affectedRows = certificateRepository.remove(delete);
        Assert.assertEquals(1, affectedRows);
    }

    @Test
    public void deleteCertificateNegative(){
        GiftCertificate delete = new GiftCertificate();
        delete.setId(220L);
        int affectedRows = certificateRepository.remove(delete);
        Assert.assertEquals(0, affectedRows);
    }
}

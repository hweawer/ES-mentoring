package com.epam.esm.service;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.repository.CertificatesRepository;
import com.epam.esm.service.certificate.impl.DeleteCertificateServiceImpl;
import com.epam.esm.service.exception.EntityNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeleteCertificateTest {
    @Mock
    private CertificatesRepository certificatesRepository;

    @InjectMocks
    private DeleteCertificateServiceImpl deleteCertificateService;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testDeleteCertificate(){
        GiftCertificate certificate = new GiftCertificate(12L, "certificate", "description", new BigDecimal(100), LocalDate.of(2018, 8, 9), null, (short)10, new HashSet<>());
        when(certificatesRepository.findById(12L)).thenReturn(Optional.of(certificate));
        deleteCertificateService.deleteCertificate(12L);
        verify(certificatesRepository, times(1)).delete(certificate);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testNegativeDeleteCertificate(){
        deleteCertificateService.deleteCertificate(12L);
    }

}

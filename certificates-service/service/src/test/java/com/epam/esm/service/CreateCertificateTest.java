package com.epam.esm.service;

import com.epam.esm.repository.CertificatesRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.certificate.impl.CreateCertificateServiceImpl;
import com.epam.esm.service.dto.CertificateDto;
import com.epam.esm.service.dto.mapper.CertificateFullUpdateMapper;
import com.epam.esm.service.dto.mapper.CertificateMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateCertificateTest {
    @Mock
    private CertificatesRepository certificateRepository;
    @InjectMocks
    private CreateCertificateServiceImpl createCertificateService;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateCertificate(){
        CertificateDto certificateDto = new CertificateDto();
        certificateDto.setId(12L);
        certificateDto.setName("certificate");
        certificateDto.setDescription("description");
        certificateDto.setDuration((short)12);
        certificateDto.setPrice(new BigDecimal(100));
        createCertificateService.createCertificate(certificateDto);
        verify(certificateRepository, times(1)).create(CertificateFullUpdateMapper.INSTANCE.toEntity(certificateDto));
    }

}

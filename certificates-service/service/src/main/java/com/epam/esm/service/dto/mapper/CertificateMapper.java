package com.epam.esm.service.dto.mapper;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.service.dto.CertificateDto;
import org.mapstruct.MappingTarget;

public interface CertificateMapper {
    CertificateDto toDto(GiftCertificate certificate);
    GiftCertificate toEntity(CertificateDto certificateDto);
    void updateCertificateFromDto(CertificateDto certificateDto, @MappingTarget GiftCertificate certificate);
}

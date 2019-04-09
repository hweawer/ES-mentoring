package com.epam.esm.service.dto;

import com.epam.esm.entity.GiftCertificate;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CertificateMapper {
    CertificateMapper INSTANCE = Mappers.getMapper(CertificateMapper.class);

    CertificateDto toDto(GiftCertificate certificate);
    GiftCertificate toEntity(CertificateDto certificateDto);
}

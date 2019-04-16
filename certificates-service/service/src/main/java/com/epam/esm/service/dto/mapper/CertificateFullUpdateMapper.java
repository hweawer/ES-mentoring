package com.epam.esm.service.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
public interface CertificateFullUpdateMapper extends CertificateMapper{
    CertificateFullUpdateMapper INSTANCE = Mappers.getMapper(CertificateFullUpdateMapper.class);
}

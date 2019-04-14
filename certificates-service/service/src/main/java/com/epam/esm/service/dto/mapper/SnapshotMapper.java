package com.epam.esm.service.dto.mapper;

import com.epam.esm.entity.CertificateSnapshot;
import com.epam.esm.service.dto.SnapshotDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SnapshotMapper {
    SnapshotMapper INSTANCE = Mappers.getMapper(SnapshotMapper.class);

    SnapshotDto toDto(CertificateSnapshot order);
    CertificateSnapshot toEntity(SnapshotDto orderDto);
}

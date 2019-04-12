package com.epam.esm.service.update;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.repository.CrudRepository;
import com.epam.esm.service.dto.CertificateDto;
import com.epam.esm.service.dto.mapper.CertificateMapper;
import com.epam.esm.service.dto.mapper.TagMapper;
import com.epam.esm.service.exception.EntityNotFoundException;
import com.epam.esm.service.update.builder.UpdateCertificateBuilder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.util.stream.Collectors.toSet;

@RequiredArgsConstructor
@Service
public class UpdateCertificateService implements UpdateEntity<Long, CertificateDto> {
    @NonNull
    private CrudRepository<GiftCertificate> certificateRepository;
    @NonNull
    private UpdateCertificateBuilder updateBuilder;

    @Transactional
    public CertificateDto update(Long id, CertificateDto dto){
        GiftCertificate certificate = certificateRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(""));

        updateBuilder.update(certificate)
                .updateName(dto.getName())
                .updateDescription(dto.getDescription())
                .updatePrice(dto.getPrice())
                .updateDuration(dto.getDuration())
                .updateTags(dto.getTags().stream()
                                        .map(TagMapper.INSTANCE::toEntity)
                                        .collect(toSet()))
                .build();
        return CertificateMapper.INSTANCE.toDto(certificateRepository.update(certificate));
    }
}

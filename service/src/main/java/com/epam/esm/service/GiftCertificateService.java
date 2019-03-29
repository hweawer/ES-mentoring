package com.epam.esm.service;

import com.epam.esm.service.dto.GiftCertificateDTO;
import com.epam.esm.service.exception.EntityNotFoundException;

import java.util.List;

public interface GiftCertificateService {
    GiftCertificateDTO create(GiftCertificateDTO t);
    int delete(Long id);
    void update(GiftCertificateDTO t);
    GiftCertificateDTO findById(Long id) throws EntityNotFoundException;
    List<GiftCertificateDTO> findByClause(String tag, String filterAttribute, String filterValue,
                                          String orderAttribute);
}

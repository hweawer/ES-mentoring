package com.epam.esm.service;

import com.epam.esm.service.dto.GiftCertificateDTO;
import com.epam.esm.service.exception.EntityNotFoundException;

import java.util.List;

public interface GiftCertificateService {
    GiftCertificateDTO create(GiftCertificateDTO t);
    void delete(Long id);
    void update(GiftCertificateDTO t);
    GiftCertificateDTO findById(Long id) throws EntityNotFoundException;
    List<GiftCertificateDTO> findByClause(String tag, String filterColumn, String filterValue,
                                          String orderColumn);
}

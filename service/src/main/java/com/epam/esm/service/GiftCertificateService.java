package com.epam.esm.service;

import com.epam.esm.service.dto.GiftCertificateDTO;
import com.epam.esm.service.exception.EntityNotFoundException;

import java.util.List;

public interface GiftCertificateService {
    GiftCertificateDTO create(GiftCertificateDTO t);
    Integer delete(Long id);
    List<GiftCertificateDTO> findAll();
    Integer update(GiftCertificateDTO t);
    GiftCertificateDTO findById(Long id) throws EntityNotFoundException;
    List<GiftCertificateDTO> findByTag(String name);
    List<GiftCertificateDTO> findSortedByName(boolean asc);
    List<GiftCertificateDTO> findSortedByDate(boolean asc);
    List<GiftCertificateDTO> findByTagSortedByName(String name, boolean asc);
    List<GiftCertificateDTO> findByTagSortedByDate(String name, boolean asc);
    List<GiftCertificateDTO> findByNamePart(String part);
    List<GiftCertificateDTO> findByDescriptionPart(String part);
    List<GiftCertificateDTO> findByNamePartSortedByName(String part, boolean asc);
    List<GiftCertificateDTO> findByNamePartSortedByDate(String part, boolean asc);
    List<GiftCertificateDTO> findByDescriptionPartSortedByDate(String part, boolean asc);
    List<GiftCertificateDTO> findByDescriptionPartSortedByName(String part, boolean asc);
    List<GiftCertificateDTO> findByTagByNamePartSortedByName(String tag, String part, boolean asc);
    List<GiftCertificateDTO> findByTagByNamePartSortedByDate(String tag, String part, boolean asc);
    List<GiftCertificateDTO> findByTagByDescriptionPartSortedByName(String tag, String part, boolean asc);
    List<GiftCertificateDTO> findByTagByDescriptionPartSortedByDate(String tag, String part, boolean asc);
}

package com.epam.esm.service.find;

import com.epam.esm.service.dto.SnapshotDto;

import java.util.List;

public interface FindSnapshotService {
    List<SnapshotDto> findUserCertificates(Integer page, Integer limit, String username);
}

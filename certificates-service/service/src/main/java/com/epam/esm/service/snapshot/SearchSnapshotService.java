package com.epam.esm.service.snapshot;

import com.epam.esm.service.dto.PaginationDto;
import com.epam.esm.service.dto.SnapshotDto;

public interface SearchSnapshotService {
    PaginationDto<SnapshotDto> findUserCertificates(Integer page, Integer limit, String username);
}

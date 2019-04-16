package com.epam.esm.service.snapshot;

import com.epam.esm.service.dto.PaginationInfoDto;
import com.epam.esm.service.dto.SnapshotDto;

public interface SearchSnapshotService {
    PaginationInfoDto<SnapshotDto> findUserCertificates(Integer page, Integer limit, String username);
}

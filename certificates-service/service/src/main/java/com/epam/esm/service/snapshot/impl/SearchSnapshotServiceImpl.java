package com.epam.esm.service.snapshot.impl;

import com.epam.esm.entity.User;
import com.epam.esm.repository.SnapshotRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.dto.PageInfo;
import com.epam.esm.service.dto.PaginationDto;
import com.epam.esm.service.dto.PaginationInfoDto;
import com.epam.esm.service.dto.SnapshotDto;
import com.epam.esm.service.dto.mapper.SnapshotMapper;
import com.epam.esm.service.exception.IncorrectPaginationValues;
import com.epam.esm.service.snapshot.SearchSnapshotService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@AllArgsConstructor
@Service
public class SearchSnapshotServiceImpl implements SearchSnapshotService {
    private SnapshotRepository snapshotRepository;
    private UserRepository userRepository;

    @Override
    public PaginationInfoDto<SnapshotDto> findUserCertificates(Integer page, Integer limit, String username) {
        User user = userRepository.findUserByLogin(username);
        Double snapshotCount = snapshotRepository.userSnapshotsCount(user).doubleValue();
        Integer pageCount = Double.valueOf(Math.ceil(snapshotCount / limit)).intValue();
        if (page > pageCount && pageCount != 0) {
            throw new IncorrectPaginationValues("incorrect.pagination");
        }
        List<SnapshotDto> snapshots = snapshotRepository.snapshotsByUser(page, limit, user).stream()
                .map(SnapshotMapper.INSTANCE::toDto)
                .collect(toList());
        PageInfo pageInfo = new PageInfo(pageCount);
        PaginationInfoDto<SnapshotDto> paginationInfoDto = new PaginationInfoDto<>();
        paginationInfoDto.setCollection(snapshots);
        paginationInfoDto.setPageInfo(pageInfo);
        return paginationInfoDto;
    }
}

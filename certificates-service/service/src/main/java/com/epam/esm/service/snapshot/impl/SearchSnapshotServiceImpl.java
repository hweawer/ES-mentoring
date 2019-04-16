package com.epam.esm.service.snapshot.impl;

import com.epam.esm.entity.User;
import com.epam.esm.repository.SnapshotRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.dto.PaginationDto;
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
    public PaginationDto<SnapshotDto> findUserCertificates(Integer page, Integer limit, String username) {
        User user = userRepository.findUserByLogin(username);
        Double snapshotCount = snapshotRepository.userSnapshotsCount(user).doubleValue();
        Integer pageCount = Double.valueOf(Math.ceil(snapshotCount / limit)).intValue();
        if (page > pageCount && pageCount != 0) {
            throw new IncorrectPaginationValues("incorrect.pagination");
        }
        List<SnapshotDto> snapshots = snapshotRepository.snapshotsByUser(page, limit, user).stream()
                .map(SnapshotMapper.INSTANCE::toDto)
                .collect(toList());
        PaginationDto<SnapshotDto> paginationDto = new PaginationDto<>();
        paginationDto.setCollection(snapshots);
        paginationDto.setFirst("/orders/certificates?page=1&limit=" + limit);
        paginationDto.setLast("/orders/certificates?page=" + (pageCount == 0 ? 1 : pageCount) + "&limit=" + limit);
        String previous = page == 1 ? null : "/orders/certificates?page=" + (page - 1) + "&limit=" + limit;
        String next = page.equals(pageCount == 0 ? 1 : pageCount) ? null : "/orders/certificates?page=" + (page + 1) + "&limit=" + limit;
        paginationDto.setPrevious(previous);
        paginationDto.setNext(next);
        return paginationDto;
    }
}

package com.epam.esm.service;

import com.epam.esm.entity.CertificateSnapshot;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.User;
import com.epam.esm.repository.SnapshotRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.dto.PageInfo;
import com.epam.esm.service.dto.PaginationInfoDto;
import com.epam.esm.service.dto.SnapshotDto;
import com.epam.esm.service.dto.mapper.SnapshotMapper;
import com.epam.esm.service.snapshot.impl.SearchSnapshotServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SearchSnapshotTest {
    @Mock
    private SnapshotRepository snapshotRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private SearchSnapshotServiceImpl snapshotService;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSearchSnapshots(){
        Integer page = 10;
        Integer limit = 5;
        String username = "username";

        User user = new User(1L, username, "password", new BigDecimal(100), null);
        GiftCertificate certificate = new GiftCertificate(1L,
                        "certificate",
                        "description",
                        new BigDecimal(100),
                        LocalDate.of(2018, 10, 12),
                        null,
                        (short)10,
                        new HashSet<>());
        CertificateSnapshot snapshot =  new CertificateSnapshot(certificate);
        List<SnapshotDto> snapshotDtos = List.of(snapshot).stream()
                                                            .map(SnapshotMapper.INSTANCE::toDto)
                                                            .collect(toList());

        when(userRepository.findUserByLogin(username)).thenReturn(user);
        when(snapshotRepository.userSnapshotsCount(user)).thenReturn(1000L);
        when(snapshotRepository.snapshotsByUser(page, limit, user)).thenReturn(List.of(snapshot));

        PaginationInfoDto<SnapshotDto> paginationInfo = new PaginationInfoDto<>();
        paginationInfo.setCollection(snapshotDtos);
        paginationInfo.setPageInfo(new PageInfo(Double.valueOf(Math.ceil(1000L / limit)).intValue()));

        Assert.assertEquals(paginationInfo, snapshotService.findUserCertificates(page, limit, username));
    }
}

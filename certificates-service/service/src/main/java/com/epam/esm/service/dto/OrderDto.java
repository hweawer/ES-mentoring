package com.epam.esm.service.dto;

import com.epam.esm.entity.CertificateSnapshot;
import com.epam.esm.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class OrderDto {
    private Long id;
    private User user;
    private LocalDateTime timestamp;
    private List<CertificateSnapshot> snapshots = new ArrayList<>();
}

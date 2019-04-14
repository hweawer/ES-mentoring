package com.epam.esm.service.dto;

import com.epam.esm.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class OrderDto {
    private Long id;
    private User user;
    private LocalDateTime timestamp;
    @Valid
    private List<SnapshotDto> certificates;
}

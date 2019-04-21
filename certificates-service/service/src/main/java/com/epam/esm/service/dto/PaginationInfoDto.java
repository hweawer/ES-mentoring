package com.epam.esm.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Collection;

@Data
@AllArgsConstructor
public class PaginationInfoDto<T> {
    private Collection<T> collection;
    private PageInfo pageInfo;
}

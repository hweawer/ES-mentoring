package com.epam.esm.service.dto.mapper;

import com.epam.esm.entity.Tag;
import com.epam.esm.service.dto.TagDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TagMapper {
    TagMapper INSTANCE = Mappers.getMapper( TagMapper.class );

    TagDto toDto(Tag tag);
    Tag toEntity(TagDto tagDto);
}



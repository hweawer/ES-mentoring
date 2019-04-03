package com.epam.esm.service.dto;

import com.epam.esm.entity.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TagMapper {
    TagMapper INSTANCE = Mappers.getMapper( TagMapper.class );

    TagDto tagToTagDto(Tag tag);
    Tag tagDtoToTag(TagDto tagDto);
}



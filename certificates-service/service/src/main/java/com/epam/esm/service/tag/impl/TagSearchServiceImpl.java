package com.epam.esm.service.tag.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.dto.PaginationDto;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.dto.mapper.TagMapper;
import com.epam.esm.service.exception.EntityNotFoundException;
import com.epam.esm.service.exception.IncorrectPaginationValues;
import com.epam.esm.service.tag.TagSearchService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static java.util.stream.Collectors.toSet;

@AllArgsConstructor
@Service
public class TagSearchServiceImpl implements TagSearchService {
    private TagRepository tagRepository;
    private OrderRepository orderRepository;

    @Transactional(readOnly = true)
    @Override
    public PaginationDto<TagDto> findAll(Integer page, Integer limit) {
        Double count = Double.valueOf(tagRepository.count());
        Integer pageCount = Double.valueOf(Math.ceil(count / limit)).intValue();
        if (page > pageCount && pageCount != 0){
            throw new IncorrectPaginationValues("incorrect.pagination");
        }
        PaginationDto<TagDto> paginationDto = new PaginationDto<>();
        paginationDto.setCollection(tagRepository.findAll(page, limit)
                .map(TagMapper.INSTANCE::toDto)
                .collect(toSet()));
        paginationDto.setFirst("/tags?page=1&limit=" + limit);
        paginationDto.setLast("/tags?page=" + (pageCount == 0 ? 1 : pageCount) + "&limit=" + limit);
        String previous = page == 1 ? null : "/tags?page=" + (page - 1) + "&limit=" + limit;
        String next = page.equals(pageCount == 0 ? 1 : pageCount) ? null : "/tags?page=" + (page + 1) + "&limit=" + limit;
        paginationDto.setPrevious(previous);
        paginationDto.setNext(next);
        return paginationDto;
    }

    @Transactional(readOnly = true)
    @Override
    public TagDto findById(Long id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("tag.not.found"));
        return TagMapper.INSTANCE.toDto(tag);
    }

    @Transactional(readOnly = true)
    @Override
    public TagDto findByName(String name) {
        Tag tag = tagRepository.findTagByName(name)
                .orElseThrow(() -> new EntityNotFoundException("tag.not.found"));
        return TagMapper.INSTANCE.toDto(tag);
    }

    @Transactional(readOnly = true)
    @Override
    public TagDto mostPopularUserTag(){
        Optional<Tag> optionalTag = orderRepository.mostPopularUserTag();
        Tag tag = optionalTag.orElseThrow(() -> new EntityNotFoundException("tag.not.found"));
        return TagMapper.INSTANCE.toDto(tag);
    }
}

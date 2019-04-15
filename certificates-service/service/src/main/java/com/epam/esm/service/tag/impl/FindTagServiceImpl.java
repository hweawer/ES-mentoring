package com.epam.esm.service.tag.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.dto.mapper.TagMapper;
import com.epam.esm.service.exception.EntityNotFoundException;
import com.epam.esm.service.exception.IncorrectPaginationValues;
import com.epam.esm.service.tag.FindTagService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@AllArgsConstructor
@Service
public class FindTagServiceImpl implements FindTagService {
    private TagRepository tagRepository;
    private OrderRepository orderRepository;
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    @Override
    public Set<TagDto> findAll(Integer page, Integer limit) {
        Double tagCount = Double.valueOf(tagRepository.count());
        Double div = tagCount / limit;
        div = div % limit == 0 ? div : div + 1;
        if (page > div){
            throw new IncorrectPaginationValues("incorrect.pagination");
        }

        return tagRepository.findAll(page, limit)
                .map(TagMapper.INSTANCE::toDto)
                .collect(toSet());
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
    public TagDto mostPopularUserTag(String username){
        User user = userRepository.findUserByLogin(username)
                .orElseThrow(() -> new EntityNotFoundException("user.not.found"));
        Optional<Tag> optionalTag = orderRepository.mostPopularUserTag(user);
        Tag tag = optionalTag.orElseThrow(() -> new EntityNotFoundException("tag.not.found"));
        return TagMapper.INSTANCE.toDto(tag);
    }
}

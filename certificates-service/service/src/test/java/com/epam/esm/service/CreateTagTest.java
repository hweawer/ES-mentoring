package com.epam.esm.service;

import com.epam.esm.entity.Tag;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.exception.InstanceExistsException;
import com.epam.esm.service.tag.impl.CreateTagServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateTagTest {
    @Mock
    private TagRepository tagRepository;
    @InjectMocks
    private CreateTagServiceImpl createTagService;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createTag(){
        TagDto tagDto = new TagDto();
        tagDto.setName("NewTag");

        Tag tag = new Tag();
        tag.setName("NewTag");

        when(tagRepository.findTagByName("NewTag")).thenReturn(Optional.empty());
        createTagService.createTag(tagDto);

        verify(tagRepository, times(1)).create(tag);
    }

    @Test(expected = InstanceExistsException.class)
    public void createTagNegative(){
        TagDto tagDto = new TagDto();
        tagDto.setName("OldTag");

        when(tagRepository.findTagByName("OldTag")).thenReturn(Optional.of(new Tag()));
        createTagService.createTag(tagDto);
    }
}

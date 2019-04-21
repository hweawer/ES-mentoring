package com.epam.esm.service;

import com.epam.esm.entity.Tag;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.exception.EntityNotFoundException;
import com.epam.esm.service.tag.impl.DeleteTagServiceImpl;
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
public class DeleteTagTest {
    @Mock
    private TagRepository tagRepository;
    @InjectMocks
    private DeleteTagServiceImpl deleteTagService;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testDeleteTagNegative(){
        when(tagRepository.findById(12L)).thenReturn(Optional.empty());
        deleteTagService.deleteTag(12L);
    }

    @Test
    public void testDeleteTag(){
        Tag tag = new Tag();
        tag.setId(12L);
        when(tagRepository.findById(12L)).thenReturn(Optional.of(tag));
        deleteTagService.deleteTag(12L);
        verify(tagRepository, times(1)).delete(tag);
    }

}

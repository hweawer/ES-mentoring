package com.epam.esm.service;

import com.epam.esm.entity.Tag;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.exception.EntityNotFoundException;
import com.epam.esm.service.tag.impl.TagSearchServiceImpl;
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
public class SearchTagTest {
    @Mock
    private TagRepository tagRepository;
    @Mock
    private OrderRepository orderRepository;
    @InjectMocks
    private TagSearchServiceImpl tagSearchService;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindAllTags(){
        tagSearchService.findAll(5, 10);
        verify(tagRepository, times(1)).findAll(5, 10);
    }

    @Test
    public void testFindTagById(){
        Tag tag = new Tag();
        tag.setId(5L);

        when(tagRepository.findById(5L)).thenReturn(Optional.of(tag));
        tagSearchService.findById(5L);

        verify(tagRepository, times(1)).findById(5L);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testFindTagByIdNegative(){
        tagSearchService.findById(100L);
    }

    @Test
    public void testFindTagByName(){
        Tag tag = new Tag();
        tag.setName("Name");

        when(tagRepository.findTagByName("Name")).thenReturn(Optional.of(tag));
        tagSearchService.findByName("Name");

        verify(tagRepository, times(1)).findTagByName("Name");
    }

    @Test(expected = EntityNotFoundException.class)
    public void testFindTagByNameNegative(){
        tagSearchService.findByName("Name");
    }

    @Test
    public void testFindMostPopularTag(){
        Tag tag = new Tag();
        tag.setName("Popular tag");

        when(orderRepository.mostPopularUserTag()).thenReturn(Optional.of(tag));
        tagSearchService.mostPopularUserTag();

        verify(orderRepository, times(1)).mostPopularUserTag();
    }

    @Test(expected = EntityNotFoundException.class)
    public void testFindMostPopularTagNegative(){
        tagSearchService.mostPopularUserTag();
    }
}

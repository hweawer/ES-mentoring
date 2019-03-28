package esm.epam.service.unit;

import com.epam.esm.entity.Tag;
import com.epam.esm.repository.repository.TagRepository;
import com.epam.esm.service.dto.TagDTO;
import com.epam.esm.service.impl.TagServiceDatabase;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TagServiceTest {
    @InjectMocks
    private static TagServiceDatabase service;
    @Mock
    private static TagRepository repository;

    @BeforeAll
    public static void setup() {
        service = new TagServiceDatabase(repository, new ModelMapper());
    }

    @Test
    void insertTest() {
        when(repository.create(any(Tag.class)))
                .thenAnswer((Answer<Tag>) invocation -> {
                    Tag tag1 = (Tag) invocation.getArguments()[0];
                    tag1.setId(1L);
                    return tag1;
                });

        TagDTO TAGdto = new TagDTO();
        TAGdto.setName("anotherName");
        TAGdto = service.create(TAGdto);
        assertNotNull(TAGdto.getId());
        assertTrue(TAGdto.getId()>0);
    }
}

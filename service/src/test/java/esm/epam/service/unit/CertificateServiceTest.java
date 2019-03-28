package esm.epam.service.unit;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.repository.repository.CertificateRepository;
import com.epam.esm.repository.repository.TagRepository;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.dto.GiftCertificateDTO;
import com.epam.esm.service.dto.TagDTO;
import com.epam.esm.service.impl.GiftCertificateServiceDatabase;
import com.epam.esm.service.impl.TagServiceDatabase;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CertificateServiceTest {
    @InjectMocks
    private static GiftCertificateService service;
    @Mock
    private static CertificateRepository certificateRepository;
    @Mock
    private static TagRepository tagRepository;

    @BeforeAll
    public static void setup() {
        service = new GiftCertificateServiceDatabase(certificateRepository, tagRepository, new ModelMapper());
    }

    @Test
    void insertTestNoTags() {
        when(certificateRepository.create(any(GiftCertificate.class)))
                .thenAnswer((Answer<GiftCertificate>) invocation -> {
                    GiftCertificate certificate = (GiftCertificate) invocation.getArguments()[0];
                    certificate.setId(1L);
                    return certificate;
                });

        GiftCertificateDTO certificateDTO = new GiftCertificateDTO();
        certificateDTO.setName("Name");
        certificateDTO.setDescription("Description");
        certificateDTO.setCreationDate(LocalDate.now());
        certificateDTO.setDuration((short) 15);
        certificateDTO.setPrice(new BigDecimal(20.0));
        certificateDTO = service.create(certificateDTO);
        assertNotNull(certificateDTO.getId());
        assertEquals(certificateDTO.getName(), "Name");
        assertEquals(certificateDTO.getDescription(), "Description");
        assertEquals(certificateDTO.getDuration(), Short.valueOf((short) 15));
        assertEquals(certificateDTO.getPrice(), new BigDecimal(20.0));
        assertTrue(certificateDTO.getId()>0);
    }

    @Test
    void insertTestWithTags() {
        when(certificateRepository.create(any(GiftCertificate.class)))
                .thenAnswer((Answer<GiftCertificate>) invocation -> {
                    GiftCertificate certificate = (GiftCertificate) invocation.getArguments()[0];
                    certificate.setId(1L);
                    return certificate;
                });
        when(tagRepository.create(any(Tag.class)))
                .thenAnswer((Answer<Tag>) invocation -> {
                    Tag tag = (Tag) invocation.getArguments()[0];
                    return tag;
                });

        GiftCertificateDTO certificateDTO = new GiftCertificateDTO();
        Set<TagDTO> tags = new HashSet<>();
        TagDTO tagDTO = new TagDTO();
        tagDTO.setName("Tag1");
        tags.add(tagDTO);
        certificateDTO.setName("Name");
        certificateDTO.setDescription("Description");
        certificateDTO.setCreationDate(LocalDate.now());
        certificateDTO.setDuration((short) 15);
        certificateDTO.setPrice(new BigDecimal(20.0));
        certificateDTO.setTags(tags);
        certificateDTO = service.create(certificateDTO);
        assertNotNull(certificateDTO.getId());
        assertEquals(certificateDTO.getName(), "Name");
        assertEquals(certificateDTO.getDescription(), "Description");
        assertEquals(certificateDTO.getDuration(), Short.valueOf((short) 15));
        assertEquals(certificateDTO.getPrice(), new BigDecimal(20.0));
        assertEquals(certificateDTO.getTags(), tags);
        assertTrue(certificateDTO.getId()>0);
    }
}

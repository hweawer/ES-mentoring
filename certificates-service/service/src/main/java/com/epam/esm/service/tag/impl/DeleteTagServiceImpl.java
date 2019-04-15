package com.epam.esm.service.tag.impl;

import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.tag.DeleteTagService;
import com.epam.esm.service.exception.EntityNotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class DeleteTagServiceImpl implements DeleteTagService {
    @NonNull
    private TagRepository tagRepository;

    @Transactional
    @Override
    public void deleteTag(Long id) {
        tagRepository.findById(id)
                .ifPresentOrElse(tag -> tagRepository.delete(tag), () -> new EntityNotFoundException("tag.not.found"));
    }
}

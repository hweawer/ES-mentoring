package com.epam.esm.service.application;

import com.epam.esm.entity.Tag;

public interface TagService extends BasicService<Tag> {
    Tag tagByName(String name);
}

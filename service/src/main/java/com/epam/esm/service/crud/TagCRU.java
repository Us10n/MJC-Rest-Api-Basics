package com.epam.esm.service.crud;

import com.epam.esm.repository.entity.Tag;
import com.epam.esm.service.dto.TagDto;

public interface TagCRU extends CRD<TagDto, Tag> {
    TagDto convertToDto(Tag tag);
    Tag convertToModel(TagDto tagDto);
}

package com.epam.esm.service.crud;

import com.epam.esm.repository.entity.Tag;
import com.epam.esm.service.dto.TagDto;

/**
 * Interface that provides CRUD operations for Tags
 */
public interface TagCRU extends CRD<TagDto, Tag> {
    /**
     * Convert {@link Tag} to {@link TagDto}.
     *
     * @param tag the tag object
     * @return the tagDto object
     */
    TagDto convertToDto(Tag tag);

    /**
     * Convert {@link TagDto} to {@link Tag}
     *
     * @param tagDto the tagDto object
     * @return the tag object
     */
    Tag convertToModel(TagDto tagDto);
}

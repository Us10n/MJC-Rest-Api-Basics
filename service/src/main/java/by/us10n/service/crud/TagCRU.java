package by.us10n.service.crud;

import by.us10n.repository.entity.Tag;
import by.us10n.service.dto.TagDto;

public interface TagCRU extends CRD<TagDto, Tag> {
    TagDto convertToDto(Tag tag);
    Tag convertToModel(TagDto tagDto);
}

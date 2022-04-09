package by.us10n.service.crud.impl;

import by.us10n.repository.dao.TagDao;
import by.us10n.repository.entity.Tag;
import by.us10n.service.crud.TagCRU;
import by.us10n.service.dto.TagDto;
import by.us10n.service.exception.ResponseException;
import by.us10n.service.validator.impl.TagValidatorImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TagCRUImpl implements TagCRU {

    private final TagDao tagDao;

    @Autowired
    public TagCRUImpl(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    @Override
    public TagDto create(TagDto object) {
        if (isTagDtoValid(object)
                && !tagDao.findByName(object.getName()).isPresent()) {
            Tag tagModel = convertToModel(object);
            Optional<Tag> createdTag = tagDao.create(tagModel);
            if (createdTag.isPresent()) {
                return convertToDto(createdTag.get());
            }
        }

        throw new ResponseException(HttpStatus.BAD_REQUEST);
    }

    @Override
    public List<TagDto> createAll(List<TagDto> objects) {
        return objects.stream().map(this::create).collect(Collectors.toList());
    }

    @Override
    public List<TagDto> readAll() {
        return tagDao.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public TagDto readById(long id) {
        Optional<Tag> optionalTag = tagDao.findById(id);
        if (!optionalTag.isPresent()) {
            throw new ResponseException(HttpStatus.NOT_FOUND);
        }
        return convertToDto(optionalTag.get());
    }

    @Override
    public void delete(long id) {
        if (!tagDao.findById(id).isPresent()) {
            throw new ResponseException(HttpStatus.NOT_FOUND);
        }
        tagDao.delete(id);
    }

    @Override
    public TagDto convertToDto(Tag tag) {
        TagDto tagDto = new TagDto();
        tagDto.setTagId(tag.getId());
        tagDto.setName(tag.getName());
        return tagDto;
    }

    @Override
    public Tag convertToModel(TagDto tagDto) {
        return new Tag(tagDto.getTagId(), tagDto.getName());
    }

    private boolean isTagDtoValid(TagDto tagDto) {
        TagValidatorImpl validator = TagValidatorImpl.getInstance();
        return tagDto != null && validator.isNameValid(tagDto.getName());
    }
}

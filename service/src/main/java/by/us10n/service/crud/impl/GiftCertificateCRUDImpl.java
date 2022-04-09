package by.us10n.service.crud.impl;

import by.us10n.repository.dao.GiftCertificateDao;
import by.us10n.repository.dao.TagDao;
import by.us10n.repository.entity.GiftCertificate;
import by.us10n.repository.entity.Tag;
import by.us10n.service.crud.GiftCertificateCRUD;
import by.us10n.service.crud.TagCRU;
import by.us10n.service.dto.GiftCertificateDto;
import by.us10n.service.exception.ResponseException;
import by.us10n.service.handler.DateHandler;
import by.us10n.service.validator.GiftCertificateValidator;
import by.us10n.service.validator.impl.GiftCertificateValidatorImpl;
import by.us10n.service.validator.impl.TagValidatorImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GiftCertificateCRUDImpl implements GiftCertificateCRUD {

    private GiftCertificateDao giftCertificateDao;
    private TagDao tagDao;
    private DateHandler dateHandler;
    private TagCRU tagCRU;

    @Autowired
    public GiftCertificateCRUDImpl(GiftCertificateDao giftCertificateDao, TagDao tagDao, TagCRU tagCRU, DateHandler dateHandler) {
        this.giftCertificateDao = giftCertificateDao;
        this.tagDao = tagDao;
        this.tagCRU = tagCRU;
        this.dateHandler = dateHandler;
    }

    @Override
    public GiftCertificateDto create(GiftCertificateDto object) {
        if (isGiftCertificateValid(object)
                && !giftCertificateDao.findByName(object.getName()).isPresent()
                && isTagListValid(object.getTags())) {
            GiftCertificate certificateModel = convertToModel(object);
            Date currentDate = Date.valueOf(dateHandler.getCurrentDate());
            certificateModel.setCreateDate(currentDate);
            certificateModel.setLastUpdateDate(currentDate);

            Optional<GiftCertificate> createdCertificateOptional = giftCertificateDao.create(certificateModel);
            List<Tag> createdTags = createTagsIfNotExist(object.getTags());

            if (createdCertificateOptional.isPresent() && !createdTags.isEmpty()) {
                GiftCertificate createdCertificate = createdCertificateOptional.get();
                createdTags.forEach(tag -> {
                    tagDao.attachTagToCertificate(createdCertificate.getId(), tag.getName());
                });
                return readById(createdCertificate.getId());
            }
        }

        throw new ResponseException(HttpStatus.BAD_REQUEST);
    }

    @Override
    public List<GiftCertificateDto> createAll(List<GiftCertificateDto> objects) {
        return null;
    }

    @Override
    public List<GiftCertificateDto> readAll() {
        List<GiftCertificateDto> certificateList = giftCertificateDao.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
        if (certificateList.isEmpty()) {
            throw new ResponseException(HttpStatus.NOT_FOUND);
        }
        certificateList.forEach(it -> {
            List<String> tags = tagDao.findTagsByGiftCertificateId(it.getGiftCertificateId());
            it.setTags(tags);
        });
        return certificateList;
    }

    @Override
    public GiftCertificateDto readById(long id) {
        Optional<GiftCertificate> optionalGitCertificate = giftCertificateDao.findById(id);
        if (!optionalGitCertificate.isPresent()) {
            throw new ResponseException(HttpStatus.NOT_FOUND);
        }
        List<String> tags = tagDao.findTagsByGiftCertificateId(optionalGitCertificate.get().getId());
        return convertToDto(optionalGitCertificate.get(), tags);
    }

    @Override
    public GiftCertificateDto update(GiftCertificateDto object) {
        return null;
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public GiftCertificateDto convertToDto(GiftCertificate giftCertificate) {
        GiftCertificateDto giftCertificateDto = new GiftCertificateDto();
        giftCertificateDto.setGiftCertificateId(giftCertificate.getId());
        giftCertificateDto.setName(giftCertificate.getName());
        giftCertificateDto.setDescription(giftCertificate.getDescription());
        giftCertificateDto.setPrice(giftCertificate.getPrice());
        giftCertificateDto.setDuration(giftCertificate.getDuration());
        giftCertificateDto.setCreateDate(giftCertificate.getCreateDate());
        giftCertificateDto.setLastUpdateDate(giftCertificate.getLastUpdateDate());
        return giftCertificateDto;
    }

    @Override
    public GiftCertificateDto convertToDto(GiftCertificate giftCertificate, List<String> tags) {
        GiftCertificateDto giftCertificateDto = convertToDto(giftCertificate);
        giftCertificateDto.setTags(tags);
        return giftCertificateDto;
    }

    @Override
    public GiftCertificate convertToModel(GiftCertificateDto giftCertificateDto) {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(giftCertificateDto.getGiftCertificateId());
        giftCertificate.setName(giftCertificateDto.getName());
        giftCertificate.setDescription(giftCertificateDto.getDescription());
        giftCertificate.setPrice(giftCertificateDto.getPrice());
        giftCertificate.setDuration(giftCertificateDto.getDuration());
        return giftCertificate;
    }

    private boolean isGiftCertificateValid(GiftCertificateDto giftCertificateDto) {
        GiftCertificateValidator validator = GiftCertificateValidatorImpl.getInstance();
        return giftCertificateDto != null && validator.isNameValid(giftCertificateDto.getName()) && validator.isDescriptionValid(giftCertificateDto.getDescription()) && validator.isPriceValid(giftCertificateDto.getPrice()) && validator.isDurationValid(giftCertificateDto.getDuration()) && validator.isTagsValid(giftCertificateDto.getTags());
    }

    private List<Tag> createTagsIfNotExist(List<String> tags) {
        List<Tag> uniqueTags = tags.stream()
                .distinct()
                .map(Tag::new)
                .collect(Collectors.toList());
        List<Tag> existingTags = tagDao.findAll();
        uniqueTags.forEach(uniqueTag -> {
            boolean isAlreadyExists = existingTags.stream().anyMatch(existingTag -> {
                return existingTag.getName().equals(uniqueTag.getName());
            });
            if (!isAlreadyExists) {
                tagDao.create(uniqueTag);
            }
        });
        return uniqueTags;
    }

    private boolean isTagListValid(List<String> tags) {
        return tags != null && tags.stream().allMatch(TagValidatorImpl.getInstance()::isNameValid);
    }
}

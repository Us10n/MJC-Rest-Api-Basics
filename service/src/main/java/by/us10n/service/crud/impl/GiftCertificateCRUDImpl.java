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
import java.time.LocalDateTime;
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
            LocalDateTime currentDate = dateHandler.getCurrentDate();
            certificateModel.setCreateDate(currentDate);
            certificateModel.setLastUpdateDate(currentDate);

            Optional<GiftCertificate> createdCertificateOptional = giftCertificateDao.create(certificateModel);
            List<Tag> tagsToAttach = createTagsIfNotExist(object.getTags());
            if (createdCertificateOptional.isPresent() && !tagsToAttach.isEmpty()) {
                GiftCertificate createdCertificate = createdCertificateOptional.get();
                tagsToAttach.forEach(tag -> {
                    tagDao.attachTagToCertificate(createdCertificate.getId(), tag.getId());
                });
                return readById(createdCertificate.getId());
            }
        }

        throw new ResponseException(HttpStatus.BAD_REQUEST);
    }

    @Override
    public List<GiftCertificateDto> readAll() {
        List<GiftCertificateDto> certificateList = giftCertificateDao.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
        if (certificateList.isEmpty()) {
            throw new ResponseException(HttpStatus.NOT_FOUND);
        }
        certificateList.forEach(it -> {
            List<String> tags = tagDao.findTagsByGiftCertificateId(it.getGiftCertificateId())
                    .stream().map(Tag::getName)
                    .collect(Collectors.toList());
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
        List<String> tags = tagDao.findTagsByGiftCertificateId(optionalGitCertificate.get().getId())
                .stream().map(Tag::getName)
                .collect(Collectors.toList());
        return convertToDto(optionalGitCertificate.get(), tags);
    }

    @Override
    public GiftCertificateDto update(GiftCertificateDto object) {
        if (object != null) {
            //copy values from existing certificate
            GiftCertificateDto currentCertificate = readById(object.getGiftCertificateId());
            if (object.getName() == null) object.setName(currentCertificate.getName());
            if (object.getDescription() == null) object.setDescription(currentCertificate.getDescription());
            if (object.getDuration() == null) object.setDuration(currentCertificate.getDuration());
            if (object.getPrice() == null) object.setPrice(currentCertificate.getPrice());
            if (object.getCreateDate() == null) object.setCreateDate(currentCertificate.getCreateDate());
            if (object.getTags() == null) object.setTags(currentCertificate.getTags());

            GiftCertificate certificateModel = convertToModel(object);
            LocalDateTime currentDate = dateHandler.getCurrentDate();
            certificateModel.setLastUpdateDate(currentDate);
            Optional<GiftCertificate> updatedCertificate = giftCertificateDao.update(certificateModel);
            List<Tag> tagToAttach = createTagsIfNotExist(object.getTags());

            if (updatedCertificate.isPresent() && !tagToAttach.isEmpty() && isTagListValid(object.getTags())) {
                //detach all tags from updating certificate
                tagDao.findTagsByGiftCertificateId(object.getGiftCertificateId()).forEach(
                        tag -> tagDao.detachTagFromCertificate(object.getGiftCertificateId(), tag.getId())
                );
                //attach new tags
                tagToAttach.forEach(tag ->
                        tagDao.attachTagToCertificate(object.getGiftCertificateId(), tag.getId())
                );
                updatedCertificate = giftCertificateDao.findById(object.getGiftCertificateId());
                if (updatedCertificate.isPresent()) {
                    return convertToDto(updatedCertificate.get(), tagToAttach.stream().map(Tag::getName).collect(Collectors.toList()));
                }
            }
        }
        throw new ResponseException(HttpStatus.NOT_FOUND);
    }

    @Override
    public void delete(long id) {
        if (!giftCertificateDao.findById(id).isPresent()) {
            throw new ResponseException(HttpStatus.NOT_FOUND);
        }
        giftCertificateDao.delete(id);
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
        List<Tag> tagsToAttach = new ArrayList<>();
        uniqueTags.forEach(uniqueTag -> {
            boolean isAlreadyExists = existingTags.stream().anyMatch(existingTag -> {
                boolean equalityFlag = existingTag.getName().equals(uniqueTag.getName());
                if (equalityFlag) {
                    tagsToAttach.add(existingTag);
                }
                return equalityFlag;
            });
            if (!isAlreadyExists) {
                tagDao.create(uniqueTag).ifPresent(tagsToAttach::add);
            }
        });
        return tagsToAttach;
    }

    private boolean isTagListValid(List<String> tags) {
        return tags != null && tags.stream().allMatch(TagValidatorImpl.getInstance()::isNameValid);
    }
}

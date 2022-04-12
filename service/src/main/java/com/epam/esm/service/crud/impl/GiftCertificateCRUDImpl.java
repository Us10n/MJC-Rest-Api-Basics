package com.epam.esm.service.crud.impl;

import com.epam.esm.repository.constants.ColumnNames;
import com.epam.esm.repository.dao.GiftCertificateDao;
import com.epam.esm.repository.dao.TagDao;
import com.epam.esm.repository.entity.GiftCertificate;
import com.epam.esm.repository.entity.Tag;
import com.epam.esm.service.criteria.GiftCertificateCriteria;
import com.epam.esm.service.crud.GiftCertificateCRUD;
import com.epam.esm.service.crud.TagCRU;
import com.epam.esm.service.dto.GiftCertificateDto;
import com.epam.esm.service.exception.ResponseException;
import com.epam.esm.service.handler.DateHandler;
import com.epam.esm.service.validator.GiftCertificateValidator;
import com.epam.esm.service.validator.impl.GiftCertificateValidatorImpl;
import com.epam.esm.service.validator.impl.TagValidatorImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GiftCertificateCRUDImpl implements GiftCertificateCRUD {

    private static final String FIND_ALL_QUERY = "SELECT DISTINCT gift_certificates.id, gift_certificates.name, gift_certificates.description, gift_certificates.price, " +
            "gift_certificates.duration, gift_certificates.create_date, gift_certificates.last_update_date " +
            "FROM module.gift_certificates " +
            "JOIN module.gift_certificate_tags ON gift_certificates.id=gift_certificate_tags.gift_certificate_id " +
            "JOIN module.tags ON gift_certificate_tags.tag_id=tags.id ";
    private static final String FIND_BY_TAG_NAME_QUERY = " tags.name='%s' ";
    private static final String FIND_BY_PART_NAME_QUERY = " gift_certificates.name like concat('%s', '%%') ";
    private static final String FIND_BY_PART_DESC_QUERY = " gift_certificates.description like concat('%s', '%%') ";
    private static final String WHERE = "where";
    private static final String AND = "and";
    private static final String ORDER_BY = "order by %s %s";

    private GiftCertificateDao giftCertificateDao;
    private TagDao tagDao;
    private DateHandler dateHandler;

    @Autowired
    public GiftCertificateCRUDImpl(GiftCertificateDao giftCertificateDao, TagDao tagDao, DateHandler dateHandler) {
        this.giftCertificateDao = giftCertificateDao;
        this.tagDao = tagDao;
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
    public List<GiftCertificateDto> readByCriteria(GiftCertificateCriteria criteria) {

        if (criteria.getSortBy() != null) {
            if (!(GiftCertificateCriteria.NAME.equalsIgnoreCase(criteria.getSortBy())
                    || GiftCertificateCriteria.DATE.equalsIgnoreCase(criteria.getSortBy()))) {
                throw new ResponseException(HttpStatus.BAD_REQUEST, new Object[]{"sort", "name", "date"});
            }
        }

        if (criteria.getSortOrder() != null) {
            if (!(GiftCertificateCriteria.ASC.equalsIgnoreCase(criteria.getSortOrder())
                    || GiftCertificateCriteria.DESC.equalsIgnoreCase(criteria.getSortOrder()))) {
                throw new ResponseException(HttpStatus.BAD_REQUEST, new Object[]{"order", "asc", "desc"});
            }
        }

        StringBuilder queryBuilder = new StringBuilder(FIND_ALL_QUERY);
        if (criteria.getTagName() != null || criteria.getPartName() != null || criteria.getPartDesc() != null) {
            queryBuilder.append(WHERE);
        }
        if (criteria.getTagName() != null) {
            final String byTag = String.format(FIND_BY_TAG_NAME_QUERY, criteria.getTagName());
            queryBuilder.append(byTag);
        }
        if (criteria.getPartName() != null) {
            if (criteria.getTagName() != null) {
                queryBuilder.append(AND);
            }
            final String partName = String.format(FIND_BY_PART_NAME_QUERY, criteria.getPartName());
            queryBuilder.append(partName);
        }
        if (criteria.getPartDesc() != null) {
            if (criteria.getTagName() != null || criteria.getPartName() != null) {
                queryBuilder.append(AND);
            }
            final String partDesc = String.format(FIND_BY_PART_DESC_QUERY, criteria.getPartDesc());
            queryBuilder.append(partDesc);
        }

        if (criteria.getSortBy() != null) {
            String sortColumn = ColumnNames.NAME.equalsIgnoreCase(criteria.getSortBy()) ? ColumnNames.NAME : ColumnNames.CREATE_DATE;
            String order = criteria.getSortOrder() != null ? criteria.getSortOrder() : GiftCertificateCriteria.ASC;
            final String orderByQuery = String.format(ORDER_BY, sortColumn, order);
            queryBuilder.append(orderByQuery);
        }

        List<GiftCertificate> foundCertificates = giftCertificateDao.findByCriteria(queryBuilder.toString());
        List<GiftCertificateDto> result = new ArrayList<>();
        foundCertificates.forEach(giftCertificate -> {
            List<String> tags = tagDao.findTagsByGiftCertificateId(giftCertificate.getId())
                    .stream().map(Tag::getName)
                    .collect(Collectors.toList());
            result.add(convertToDto(giftCertificate, tags));
        });

        return result;
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

            GiftCertificate certificateModel;
            try {
                certificateModel = convertToModel(object);
            } catch (DateTimeParseException e) {
                throw new ResponseException(HttpStatus.BAD_REQUEST, new Object[]{object.getCreateDate()});
            }
            LocalDateTime currentDate = dateHandler.getCurrentDate();
            certificateModel.setLastUpdateDate(currentDate);
            Optional<GiftCertificate> updatedCertificate = giftCertificateDao.update(certificateModel);
            List<Tag> tagToAttach = createTagsIfNotExist(object.getTags());

            if (updatedCertificate.isPresent() && isTagListValid(object.getTags())) {
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
        giftCertificate.setCreateDate(LocalDateTime.parse(giftCertificateDto.getCreateDate()));
        return giftCertificate;
    }

    private boolean isGiftCertificateValid(GiftCertificateDto giftCertificateDto) {
        GiftCertificateValidator validator = GiftCertificateValidatorImpl.getInstance();
        return giftCertificateDto != null && validator.isNameValid(giftCertificateDto.getName())
                && validator.isDescriptionValid(giftCertificateDto.getDescription())
                && validator.isPriceValid(giftCertificateDto.getPrice()) && validator.isDurationValid(giftCertificateDto.getDuration())
                && validator.isTagsValid(giftCertificateDto.getTags());
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
        return tags != null && (tags.stream().allMatch(TagValidatorImpl.getInstance()::isNameValid) || tags.isEmpty());
    }
}

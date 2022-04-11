package com.epam.esm.service.crud;

import com.epam.esm.repository.dao.GiftCertificateDao;
import com.epam.esm.repository.dao.TagDao;
import com.epam.esm.repository.entity.GiftCertificate;
import com.epam.esm.repository.entity.Tag;
import com.epam.esm.service.config.ServiceConfigTest;
import com.epam.esm.service.criteria.GiftCertificateCriteria;
import com.epam.esm.service.crud.impl.GiftCertificateCRUDImpl;
import com.epam.esm.service.crud.impl.TagCRUImpl;
import com.epam.esm.service.dto.GiftCertificateDto;
import com.epam.esm.service.handler.DateHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ServiceConfigTest.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GiftCertificateCRUDImplTest {

    @Autowired
    private GiftCertificateDao giftCertificateDao;

    @Autowired
    private TagDao tagDao;

    @Autowired
    private DateHandler dateHandler;

    private GiftCertificateCRUD giftCertificateCRUD;
    private GiftCertificateDto sampleDto;
    private GiftCertificateDto updatedSampleDto;

    @BeforeAll
    public void setup() {
        List<GiftCertificate> findAllList = new ArrayList<>();
        Mockito.when(dateHandler.getCurrentDate()).thenReturn(LocalDateTime.parse("2022-04-11T10:00:11.156"));

        GiftCertificate sample = new GiftCertificate(
                1, "test1", "test1", 1.2, 1, dateHandler.getCurrentDate(), dateHandler.getCurrentDate()
        );
        GiftCertificate updatedSample = new GiftCertificate(
                1, "upd", "upd", 1.1, 1, dateHandler.getCurrentDate(), dateHandler.getCurrentDate()
        );

        findAllList.add(sample);
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag(1, "funny"));
        tags.add(new Tag(2, "useful"));
        tags.add(new Tag(3, "great"));
        List<Tag> updatedTags = tags;
        updatedTags.add(new Tag(4, "updated"));

        Mockito.when(tagDao.findTagsByGiftCertificateId(1)).thenReturn(tags);
        Mockito.when(tagDao.findAll()).thenReturn(tags);


        Mockito.when(giftCertificateDao.findById(1)).thenReturn(Optional.of(sample));
        Mockito.when(giftCertificateDao.findAll()).thenReturn(findAllList);
        Mockito.when(giftCertificateDao.findByCriteria(Mockito.any(String.class))).thenReturn(findAllList);

        Mockito.when(giftCertificateDao.create(Mockito.any(GiftCertificate.class))).thenReturn(Optional.of(sample));
        Mockito.when(giftCertificateDao.update(Mockito.any())).thenReturn(Optional.of(updatedSample));

        tags.remove(2);

        TagCRU tagCru = new TagCRUImpl(tagDao);
        giftCertificateCRUD = new GiftCertificateCRUDImpl(giftCertificateDao, tagDao, tagCru, new DateHandler());

        sampleDto = giftCertificateCRUD.convertToDto(sample, tags.stream().map(Tag::getName).collect(Collectors.toList()));
        updatedSampleDto = giftCertificateCRUD.convertToDto(updatedSample, updatedTags.stream().map(Tag::getName).collect(Collectors.toList()));
    }

    @Test
    void create() {
        GiftCertificateDto actual = giftCertificateCRUD.create(sampleDto);
        Assertions.assertEquals(actual, sampleDto);
    }

    @Test
    void readAll() {
        GiftCertificateDto actual = giftCertificateCRUD.readAll().get(0);
        Assertions.assertEquals(actual, sampleDto);
    }

    @Test
    void readById() {
        GiftCertificateDto actual = giftCertificateCRUD.readById(1);
        Assertions.assertEquals(actual, sampleDto);
    }

    @Test
    void readByCriteria() {
        GiftCertificateCriteria criteria = new GiftCertificateCriteria();
        GiftCertificateDto actual = giftCertificateCRUD.readByCriteria(criteria).get(0);
        Assertions.assertEquals(actual,sampleDto);
    }

    @Test
    void update() {
        GiftCertificateDto newCertificate=new GiftCertificateDto(
                1, "upd", "upd", 1.1, 1, dateHandler.getCurrentDate(), dateHandler.getCurrentDate(),new ArrayList<>()
        );
        GiftCertificateDto actual =giftCertificateCRUD.update(newCertificate);
        Assertions.assertEquals(actual,updatedSampleDto);
    }

    @Test
    void delete() {
    }

    @Test
    void convertToDto() {
    }

    @Test
    void testConvertToDto() {
    }

    @Test
    void convertToModel() {
    }
}
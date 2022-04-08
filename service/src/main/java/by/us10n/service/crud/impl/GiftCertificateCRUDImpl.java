package by.us10n.service.crud.impl;

import by.us10n.repository.dao.GiftCertificateDao;
import by.us10n.repository.entity.GiftCertificate;
import by.us10n.repository.entity.Tag;
import by.us10n.service.crud.GiftCertificateCRUD;
import by.us10n.service.dto.GiftCertificateDto;
import by.us10n.service.exception.ResponseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GiftCertificateCRUDImpl implements GiftCertificateCRUD {

    private GiftCertificateDao giftCertificateDao;

    @Autowired
    public GiftCertificateCRUDImpl(GiftCertificateDao giftCertificateDao) {
        this.giftCertificateDao = giftCertificateDao;
    }

    @Override
    public GiftCertificateDto create(GiftCertificateDto object) {
//        if(object!=null){
//            if (object.getName() == null || object.getDescription() == null || object.getDuration() == 0 || object.getPrice() == 0) {
//                throw new ResponseException(HttpStatus.BAD_REQUEST);
//            }
//        }
        return null;
    }

    @Override
    public List<GiftCertificateDto> createAll(List<GiftCertificateDto> objects) {
        return null;
    }

    @Override
    public List<GiftCertificateDto> readAll() {
        return giftCertificateDao.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public GiftCertificateDto readById(long id) {
        Optional<GiftCertificate> optionalGitCertificate = giftCertificateDao.findById(id);
        return optionalGitCertificate.map(this::convertToDto).orElse(null);
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
    public GiftCertificateDto convertToDto(GiftCertificate giftCertificate, List<Tag> tags) {
        GiftCertificateDto giftCertificateDto = convertToDto(giftCertificate);
        giftCertificateDto.setTags(tags.stream().map(Tag::getName).collect(Collectors.toList()));
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
        return null;
    }
}

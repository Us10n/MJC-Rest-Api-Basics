package com.epam.esm.service.crud;

import com.epam.esm.repository.entity.GiftCertificate;
import com.epam.esm.service.criteria.GiftCertificateCriteria;
import com.epam.esm.service.dto.GiftCertificateDto;

import java.util.List;

public interface GiftCertificateCRUD extends CRUD<GiftCertificateDto,GiftCertificate>{
    GiftCertificateDto convertToDto(GiftCertificate giftCertificate, List<String> tags);
    GiftCertificateDto convertToDto(GiftCertificate giftCertificate);
    GiftCertificate convertToModel(GiftCertificateDto giftCertificateDto);
    List<GiftCertificateDto> readByCriteria(GiftCertificateCriteria criteria);
}

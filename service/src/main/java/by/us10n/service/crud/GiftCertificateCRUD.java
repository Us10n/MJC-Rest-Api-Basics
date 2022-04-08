package by.us10n.service.crud;

import by.us10n.repository.entity.GiftCertificate;
import by.us10n.repository.entity.Tag;
import by.us10n.service.dto.GiftCertificateDto;

import java.util.List;

public interface GiftCertificateCRUD extends CRUD<GiftCertificateDto,GiftCertificate>{
    GiftCertificateDto convertToDto(GiftCertificate giftCertificate, List<Tag> tags);
    GiftCertificateDto convertToDto(GiftCertificate giftCertificate);
    GiftCertificate convertToModel(GiftCertificateDto giftCertificateDto);
}

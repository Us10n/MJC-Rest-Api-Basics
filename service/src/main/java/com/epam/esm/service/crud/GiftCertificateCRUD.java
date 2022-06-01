package com.epam.esm.service.crud;

import com.epam.esm.repository.entity.GiftCertificate;
import com.epam.esm.service.criteria.GiftCertificateCriteria;
import com.epam.esm.service.dto.GiftCertificateDto;

import java.util.List;

/**
 * Interface that provides CRUD operations for GiftCertificates
 */
public interface GiftCertificateCRUD extends CRUD<GiftCertificateDto, GiftCertificate> {
    /**
     * Convert {@link GiftCertificate} to {@link GiftCertificateDto}.
     *
     * @param giftCertificate gift certificate model
     * @param tags            list of gift certificate tags
     * @return {@link GiftCertificateDto}
     */
    GiftCertificateDto convertToDto(GiftCertificate giftCertificate, List<String> tags);

    /**
     * Convert to dto gift certificate dto.
     *
     * @param giftCertificate the gift certificate
     * @return {@link GiftCertificateDto}
     */
    GiftCertificateDto convertToDto(GiftCertificate giftCertificate);

    /**
     * Convert  {@link GiftCertificateDto} to {@link GiftCertificate}.
     *
     * @param giftCertificateDto the gift certificate dto
     * @return {@link GiftCertificate}
     */
    GiftCertificate convertToModel(GiftCertificateDto giftCertificateDto);

    /**
     * Read by criteria specified in criteria object.
     *
     * @param criteria object that contains read criterias
     * @return the list of {@link GiftCertificateDto} objects
     */
    List<GiftCertificateDto> readByCriteria(GiftCertificateCriteria criteria);
}

package com.epam.esm.repository.dao;

import com.epam.esm.repository.entity.GiftCertificate;
import com.epam.esm.repository.entity.Tag;

import java.util.List;

/**
 * Data access object that perform operations with {@link Tag} model in database
 */
public interface TagDao extends Dao<Tag> {
    /**
     * Find tags that attached to {@link GiftCertificate}.
     *
     * @param id giftCertificate id
     * @return list of {@link Tag}
     */
    List<Tag> findTagsByGiftCertificateId(long id);

    /**
     * Attach tag to certificate.
     *
     * @param certificateId the certificate id
     * @param tagId         the tag id
     */
    void attachTagToCertificate(long certificateId, long tagId);

    /**
     * Detach tag from certificate.
     *
     * @param certificateId the certificate id
     * @param tagId         the tag id
     */
    void detachTagFromCertificate(long certificateId, long tagId);
}

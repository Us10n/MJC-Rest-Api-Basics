package com.epam.esm.repository.dao;

import com.epam.esm.repository.entity.Tag;

import java.util.List;

public interface TagDao extends Dao<Tag> {
    List<Tag> findTagsByGiftCertificateId(long id);
    void attachTagToCertificate(long certificateId,long tagId);
    void detachTagFromCertificate(long certificateId, long tagId);
}

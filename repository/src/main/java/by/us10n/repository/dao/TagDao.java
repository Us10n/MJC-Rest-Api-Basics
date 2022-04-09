package by.us10n.repository.dao;

import by.us10n.repository.entity.Tag;

import java.util.List;

public interface TagDao extends Dao<Tag> {
    List<String> findTagsByGiftCertificateId(long id);
    void attachTagToCertificate(long certificateId,long tagId);
    void attachTagToCertificate(long certificateId,String tagName);
    void detachTagFromCertificate(long certificateId, long tagId);
    void detachTagFromCertificate(long certificateId, String tagName);
}

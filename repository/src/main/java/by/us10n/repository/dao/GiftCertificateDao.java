package by.us10n.repository.dao;

import by.us10n.repository.entity.GiftCertificate;

import java.util.Optional;

public interface GiftCertificateDao extends Dao<GiftCertificate> {
    Optional<GiftCertificate> update(GiftCertificate giftCertificate);
}

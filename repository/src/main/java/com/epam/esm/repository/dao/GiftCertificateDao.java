package com.epam.esm.repository.dao;

import com.epam.esm.repository.entity.GiftCertificate;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateDao extends Dao<GiftCertificate> {
    Optional<GiftCertificate> update(GiftCertificate giftCertificate);
    List<GiftCertificate> findByCriteria(String query);
}

package com.epam.esm.repository.dao.impl;

import com.epam.esm.repository.dao.GiftCertificateDao;
import com.epam.esm.repository.entity.GiftCertificate;
import com.epam.esm.repository.constants.ColumnNames;
import com.epam.esm.repository.constants.TableNames;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class GiftCertificateDaoImpl implements GiftCertificateDao {
    private static final String FIND_ALL_QUERY = "SELECT gift_certificates.id, gift_certificates.name, gift_certificates.description, gift_certificates.price, " +
            "gift_certificates.duration, gift_certificates.create_date, gift_certificates.last_update_date " +
            "FROM module.gift_certificates";
    private static final String FIND_BY_ID_QUERY = FIND_ALL_QUERY + " WHERE id=?";
    private static final String FIND_BY_NAME_QUERY = FIND_ALL_QUERY + " WHERE name=?";
    private static final String DELETE_TAG_HOLDER_BY_ID_QUERY = "DELETE FROM module.gift_certificate_tags WHERE gift_certificate_id=?";
    private static final String DELETE_GIFT_CERTIFICATE_BY_ID_QUERY = "DELETE FROM module.gift_certificates WHERE id=?";
    private static final String UPDATE_GIFT_CERTIFICATE_BY_ID_QUERY = "UPDATE module.gift_certificates SET name=?, description=?, price=?," +
            " duration=?, create_date=?, last_update_date=? WHERE id=?";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GiftCertificateDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<GiftCertificate> create(GiftCertificate object) {
        Optional<GiftCertificate> createdCertificate = Optional.empty();
        try {
            Map<String, Object> keys = new SimpleJdbcInsert(this.jdbcTemplate)
                    .withTableName(TableNames.GIFT_CERTIFICATES_TABLE)
                    .usingColumns(ColumnNames.NAME, ColumnNames.DESCRIPTION, ColumnNames.PRICE, ColumnNames.DURATION, ColumnNames.CREATE_DATE, ColumnNames.LAST_UPDATE_DATE)
                    .usingGeneratedKeyColumns(ColumnNames.ID)
                    .executeAndReturnKeyHolder(new HashMap<String, Object>() {{
                        put(ColumnNames.NAME, object.getName());
                        put(ColumnNames.DESCRIPTION, object.getDescription());
                        put(ColumnNames.PRICE, object.getPrice());
                        put(ColumnNames.DURATION, object.getDuration());
                        put(ColumnNames.CREATE_DATE, object.getCreateDate());
                        put(ColumnNames.LAST_UPDATE_DATE, object.getLastUpdateDate());
                    }})
                    .getKeys();

            long id = 0;
            if (keys != null && !keys.isEmpty()) {
                String idString = keys.values().toArray()[0].toString();
                id = Long.parseLong(idString);
                createdCertificate = findById(id);
            }
        } catch (Exception e) {
            createdCertificate = Optional.empty();
        }

        return createdCertificate;
    }

    @Override
    public Optional<GiftCertificate> findById(long id) {
        return jdbcTemplate.query(FIND_BY_ID_QUERY, BeanPropertyRowMapper.newInstance(GiftCertificate.class), id).stream().findAny();
    }

    @Override
    public Optional<GiftCertificate> findByName(String name) {
        return jdbcTemplate.query(FIND_BY_NAME_QUERY, BeanPropertyRowMapper.newInstance(GiftCertificate.class), name).stream().findAny();
    }

    @Override
    public List<GiftCertificate> findAll() {
        return jdbcTemplate.query(FIND_ALL_QUERY, BeanPropertyRowMapper.newInstance(GiftCertificate.class));
    }

    @Override
    public List<GiftCertificate> findByCriteria(String query) {
        return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(GiftCertificate.class));
    }

    @Override
    @Transactional
    public void delete(long id) {
        jdbcTemplate.update(DELETE_TAG_HOLDER_BY_ID_QUERY, id);
        jdbcTemplate.update(DELETE_GIFT_CERTIFICATE_BY_ID_QUERY, id);
    }

    @Override
    public Optional<GiftCertificate> update(GiftCertificate object) {
        jdbcTemplate.update(UPDATE_GIFT_CERTIFICATE_BY_ID_QUERY, object.getName(), object.getDescription(),
                object.getPrice(), object.getDuration(), object.getCreateDate(), object.getLastUpdateDate(), object.getId());
        return findById(object.getId());
    }
}

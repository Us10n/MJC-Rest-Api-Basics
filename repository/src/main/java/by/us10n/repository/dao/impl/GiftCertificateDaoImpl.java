package by.us10n.repository.dao.impl;

import by.us10n.repository.dao.GiftCertificateDao;
import by.us10n.repository.entity.GiftCertificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static by.us10n.repository.constants.ColumnNames.*;
import static by.us10n.repository.constants.TableNames.GIFT_CERTIFICATES_TABLE;

@Repository
public class GiftCertificateDaoImpl implements GiftCertificateDao {
    private static final String FIND_ALL_QUERY = "SELECT gift_certificates.id, gift_certificates.name, gift_certificates.description, gift_certificates.price, " +
            "gift_certificates.duration, gift_certificates.create_date, gift_certificates.last_update_date " +
            "FROM module.gift_certificates";
    private static final String FIND_ALL_WITH_TAGS_QUERY = FIND_ALL_QUERY + " JOIN gift_certificate_tags ON gift_certificates.id=gift_certificate_tags.gift_certificate_id " +
            "JOIN tags ON gift_certificate_tags.tag_id=tags.id";
    private static final String FIND_BY_ID_QUERY = FIND_ALL_QUERY + " WHERE id=?";
    private static final String FIND_BY_NAME_QUERY = FIND_ALL_QUERY + " WHERE name=?";


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
                    .withTableName(GIFT_CERTIFICATES_TABLE)
                    .usingColumns(NAME, DESCRIPTION, PRICE, DURATION, CREATE_DATE, LAST_UPDATE_DATE)
                    .usingGeneratedKeyColumns(ID)
                    .executeAndReturnKeyHolder(new HashMap<String, Object>() {{
                        put(NAME, object.getName());
                        put(DESCRIPTION, object.getDescription());
                        put(PRICE, object.getPrice());
                        put(DURATION, object.getDuration());
                        put(CREATE_DATE, object.getCreateDate());
                        put(LAST_UPDATE_DATE, object.getLastUpdateDate());
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
    public void delete(long id) {

    }
}

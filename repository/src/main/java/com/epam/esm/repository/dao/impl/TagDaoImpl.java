package com.epam.esm.repository.dao.impl;

import com.epam.esm.repository.dao.TagDao;
import com.epam.esm.repository.entity.Tag;
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
public class TagDaoImpl implements TagDao {

    private static final String FIND_ALL_QUERY = "SELECT tags.id, tags.name FROM module.tags";
    private static final String FIND_BY_ID_QUERY = FIND_ALL_QUERY + " WHERE id=?";
    private static final String FIND_BY_GIFT_CERTIFICATE_ID_QUERY = "SELECT tags.id, tags.name FROM module.tags " +
            "JOIN module.gift_certificate_tags ON gift_certificate_tags.tag_id=tags.id " +
            "WHERE gift_certificate_id=?";
    private static final String FIND_BY_NAME_QUERY = FIND_ALL_QUERY + " WHERE name=?";
    private static final String DELETE_TAG_HOLDER_BY_ID_QUERY = "DELETE FROM module.gift_certificate_tags WHERE tag_id=?";
    private static final String DELETE_TAG_BY_ID_QUERY = "DELETE FROM module.tags WHERE id=?";
    private static final String ATTACH_TAG_BY_ID_QUERY = "INSERT INTO module.gift_certificate_tags (gift_certificate_id, tag_id) VALUES (?, ?)";
    private static final String DETACH_TAG_BY_ID_QUERY = "DELETE FROM module.gift_certificate_tags WHERE gift_certificate_id=? AND tag_id=?";
    private static final String SCHEMA_NAME = "module";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TagDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Tag> create(Tag object) {
        Optional<Tag> createdTag = Optional.empty();
        try {
            Map<String, Object> keys = new SimpleJdbcInsert(this.jdbcTemplate)
                    .withSchemaName(SCHEMA_NAME)
                    .withTableName(TableNames.TAGS_TABLE)
                    .usingColumns(ColumnNames.NAME)
                    .usingGeneratedKeyColumns(ColumnNames.ID)
                    .executeAndReturnKeyHolder(new HashMap<String, String>() {{
                        put(ColumnNames.NAME, object.getName());
                    }})
                    .getKeys();

            long id = 0;
            if (keys != null && !keys.isEmpty()) {
                String idString = keys.values().toArray()[0].toString();
                id = Long.parseLong(idString);
                createdTag = findById(id);
            }
        } catch (Exception e) {
            createdTag = Optional.empty();
        }

        return createdTag;
    }

    @Override
    public Optional<Tag> findById(long id) {
        return jdbcTemplate.query(FIND_BY_ID_QUERY, BeanPropertyRowMapper.newInstance(Tag.class), id).stream().findAny();
    }

    @Override
    public List<Tag> findTagsByGiftCertificateId(long id) {
        return jdbcTemplate.query(FIND_BY_GIFT_CERTIFICATE_ID_QUERY, BeanPropertyRowMapper.newInstance(Tag.class), id);
    }

    @Override
    public Optional<Tag> findByName(String name) {
        return jdbcTemplate.query(FIND_BY_NAME_QUERY, BeanPropertyRowMapper.newInstance(Tag.class), name).stream().findAny();
    }

    @Override
    public List<Tag> findAll() {
        return jdbcTemplate.query(FIND_ALL_QUERY, BeanPropertyRowMapper.newInstance(Tag.class));
    }

    @Override
    @Transactional
    public void delete(long id) {
        jdbcTemplate.update(DELETE_TAG_HOLDER_BY_ID_QUERY, id);
        jdbcTemplate.update(DELETE_TAG_BY_ID_QUERY, id);
    }

    @Override
    public void attachTagToCertificate(long certificateId, long tagId) {
        jdbcTemplate.update(ATTACH_TAG_BY_ID_QUERY, certificateId, tagId);
    }

    @Override
    public void detachTagFromCertificate(long certificateId, long tagId) {
        jdbcTemplate.update(DETACH_TAG_BY_ID_QUERY, certificateId, tagId);
    }
}

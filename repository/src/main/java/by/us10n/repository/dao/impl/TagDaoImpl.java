package by.us10n.repository.dao.impl;

import by.us10n.repository.dao.TagDao;
import by.us10n.repository.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.*;

import static by.us10n.repository.constants.ColumnNames.*;
import static by.us10n.repository.constants.TableNames.TAGS_HOLDER_TABLE;
import static by.us10n.repository.constants.TableNames.TAGS_TABLE;

@Repository
public class TagDaoImpl implements TagDao {

    private static final String FIND_ALL_QUERY = "SELECT tags.id, tags.name FROM module.tags";
    private static final String FIND_BY_ID_QUERY = FIND_ALL_QUERY + " WHERE id=?";
    private static final String FIND_BY_GIFT_CERTIFICATE_ID_QUERY = "SELECT tags.name FROM module.tags " +
            "JOIN gift_certificate_tags ON gift_certificate_tags.tag_id=tags.id " +
            "WHERE gift_certificate_id=?";
    private static final String FIND_BY_NAME_QUERY = FIND_ALL_QUERY + " WHERE name=?";
    private static final String DELETE_TAG_HOLDER_BY_ID_QUERY = "DELETE FROM module.gift_certificate_tags WHERE tag_id=?";
    private static final String DELETE_TAG_BY_ID_QUERY = "DELETE FROM module.tags WHERE id=?";
    private static final String ATTACH_TAG_BY_ID_QUERY = "INSERT INTO module.gift_certificate_tags (gift_certificate_id, tag_id) VALUES (?, ?)";
    private static final String ATTACH_TAG_BY_NAME_QUERY = "INSERT INTO module.gift_certificate_tags (gift_certificate_id, tag_id) VALUES (?, (SELECT tags.id FROM module.tags WHERE tags.name=?))";
    private static final String DETACH_TAG_BY_ID_QUERY = "DELETE FROM module.gift_certificate_tags WHERE gift_certificate_id=? AND tag_id=?";
    private static final String DETACH_TAG_BY_NAME_QUERY = "DELETE FROM module.gift_certificate_tags WHERE = gift_certificate_id=? AND tag_id=(SELECT tags.id FROM module.tags WHERE tags.name=?)";

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
                    .withTableName(TAGS_TABLE)
                    .usingColumns(NAME)
                    .usingGeneratedKeyColumns(ID)
                    .executeAndReturnKeyHolder(new HashMap<String, String>() {{
                        put(NAME, object.getName());
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
    public List<String> findTagsByGiftCertificateId(long id) {
        return jdbcTemplate.queryForList(FIND_BY_GIFT_CERTIFICATE_ID_QUERY, String.class, id);
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
    public void delete(long id) {
        jdbcTemplate.update(DELETE_TAG_HOLDER_BY_ID_QUERY, id);
        jdbcTemplate.update(DELETE_TAG_BY_ID_QUERY, id);
    }

    @Override
    public void attachTagToCertificate(long certificateId, long tagId) {
        jdbcTemplate.update(ATTACH_TAG_BY_ID_QUERY, certificateId, tagId);
    }

    @Override
    public void attachTagToCertificate(long certificateId, String tagName) {
        jdbcTemplate.update(ATTACH_TAG_BY_NAME_QUERY, certificateId, tagName);
    }

    @Override
    public void detachTagFromCertificate(long certificateId, long tagId) {
        jdbcTemplate.update(DETACH_TAG_BY_ID_QUERY, certificateId, tagId);
    }

    @Override
    public void detachTagFromCertificate(long certificateId, String tagName) {
        jdbcTemplate.update(DETACH_TAG_BY_NAME_QUERY, certificateId, tagName);
    }
}

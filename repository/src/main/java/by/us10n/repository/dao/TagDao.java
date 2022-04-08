package by.us10n.repository.dao;

import by.us10n.repository.entity.Tag;

import java.util.Optional;

public interface TagDao extends Dao<Tag> {
    Optional<Tag> findByName(String name);
}

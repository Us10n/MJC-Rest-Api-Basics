package by.us10n.repository.dao;

import by.us10n.repository.config.TestConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
@ActiveProfiles("test")
class TagDaoImplTest {

    @Autowired
    private TagDao tagDao;

    @Test
    void findById() {
    }

    @Test
    void findAll() {
    }
}
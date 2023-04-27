package ru.clevertec.ecl.repository;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.clevertec.ecl.entity.GiftCertificate;
import ru.clevertec.ecl.entity.Tag;
import ru.clevertec.ecl.exception.RepositoryException;
import ru.clevertec.ecl.util.DataStorage;

import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Testcontainers
class TagRepositoryTest {
    @Container
    private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:13")
            .withDatabaseName("postgres")
            .withUsername("test")
            .withPassword("test")
            .withInitScript("init-db.sql");
    private TagRepository tagRepository;

    @BeforeEach
    void setUp() {
        Configuration cfg = new Configuration();
        cfg.setProperty("hibernate.connection.driver_class", postgresContainer.getDriverClassName());
        cfg.setProperty("hibernate.connection.url", postgresContainer.getJdbcUrl());
        cfg.setProperty("hibernate.connection.username", postgresContainer.getUsername());
        cfg.setProperty("hibernate.connection.password", postgresContainer.getPassword());
        cfg.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        cfg.addAnnotatedClass(Tag.class);
        cfg.addAnnotatedClass(GiftCertificate.class);
        SessionFactory sessionFactory = cfg.buildSessionFactory();
        this.tagRepository = new TagRepository(sessionFactory);
    }

    @Test
    void checkFindById_shouldReturnTagWithId5() {
        //given
        Long id = 5L;
        Tag expected = DataStorage.findTagForFindById(id);

        //when
        Tag actual = tagRepository.findById(id);

        //then
        assertEquals(expected, actual);
    }

    @Test
    void checkFindByName_shouldReturnTagWithNameBowling() {
        //given
        String name = "bowling";
        Tag expected = DataStorage.findTagForFindByName(name);

        //when
        Tag actual = tagRepository.findByName(name);

        //then
        assertEquals(expected, actual);
    }

    @Test
    void checkFindAll_shouldReturnTagsWithIdsFrom1to3() {
        //given
        List<Tag> expected = DataStorage.findTagsForFindAll().subList(0, 3);

        //when
        List<Tag> actual = tagRepository.findAll(3, 0);

        //then
        assertEquals(expected, actual);
    }

    @Test
    void checkFindAll_shouldReturnTagsWithIdsFrom11to15() {
        //given
        List<Tag> expected = DataStorage.findTagsForFindAll().subList(10, 15);

        //when
        List<Tag> actual = tagRepository.findAll(5, 10);

        //then
        assertEquals(expected, actual);
    }

    @Test
    void checkAdd_shouldAddTagWithId20AndNameHello() {
        //given
        Tag expected = DataStorage.findTagForAdd();

        //when
        tagRepository.add(new Tag(null, "Hello", new HashSet<>()));
        Tag actual = tagRepository.findById(20L);

        //then
        assertEquals(expected, actual);
    }

    @Test
    void checkUpdate_shouldUpdateTag() {
        //given
        Tag expected = DataStorage.findTagForUpdate();

        //when
        tagRepository.update(expected);
        Tag actual = tagRepository.findById(expected.getId());

        //then
        assertEquals(expected, actual);
    }

    @Test
    void checkDelete_shouldDeleteTagWithId15AndThrowExceptionWhenTryingToGetDeletedTag() {
        //given
        Long id = 15L;

        //when
        tagRepository.delete(id);

        //then
        assertThrows(RepositoryException.class, () -> tagRepository.findById(id));
    }
}
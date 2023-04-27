package ru.clevertec.ecl.repository;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.clevertec.ecl.entity.GiftCertificate;
import ru.clevertec.ecl.entity.Tag;
import ru.clevertec.ecl.exception.RepositoryException;
import ru.clevertec.ecl.util.DataStorage;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
class GiftCertificateRepositoryTest {
    @Container
    private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:13")
            .withDatabaseName("postgres")
            .withUsername("test")
            .withPassword("test")
            .withInitScript("init-db.sql");
    private CertificateRepository certificateRepository;
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
        this.certificateRepository = new CertificateRepository(sessionFactory);
    }

    @Test
    void checkFindById_shouldReturnCertificateWithId5() {
        //given
        Long id = 6L;
        GiftCertificate expected = DataStorage.findCertificateForFindById(id);

        //when
        GiftCertificate actual = certificateRepository.findById(id);

        //then
        assertEquals(expected, actual);
    }

    @Nested
    class FindAllTest {
        @Test
        void checkFindAll_shouldReturnCertificatesWithIdsFrom1To5() {
            //given
            List<GiftCertificate> expected = DataStorage.findCertificateForFindAll().subList(0, 5);

            //when
            List<GiftCertificate> actual = certificateRepository
                    .findAll(null, null, null, null, null, 5, 0);

            //then
            assertEquals(expected, actual);
        }

        @Test
        void checkFindAll_shouldReturnCertificatesWithIdsFrom5To8() {
            //given
            List<GiftCertificate> expected = DataStorage.findCertificateForFindAll().subList(4, 8);

            //when
            List<GiftCertificate> actual = certificateRepository
                    .findAll(null, null, null, null, null, 4, 4);

            //then
            assertEquals(expected, actual);
        }

        @Test
        void checkFindAll_shouldReturnFirst4CertificatesWithCosmetologyTag() {
            //given
            List<GiftCertificate> expected = DataStorage.findCertificateForFindAllWithCosmetologyTagFilter();

            //when
            List<GiftCertificate> actual = certificateRepository
                    .findAll("cosmetology", null, null, null, null, 4, 0);

            //then
            assertEquals(expected, actual);
        }

        @Test
        void checkFindAll_shouldReturnFirst3CertificatesWithCenterWordInTheirName() {
            //given
            List<GiftCertificate> expected = DataStorage.findCertificateForFindAllWithPartOfNameFilter();

            //when
            List<GiftCertificate> actual = certificateRepository
                    .findAll(null, "center", null, null, null, 3, 0);

            //then
            assertEquals(expected, actual);
        }

        @Test
        void checkFindAll_shouldReturn2CertificatesWithInThePhraseInTheirDescriptionSkippingTheFirst2Certificates() {
            //given
            List<GiftCertificate> expected = DataStorage.findCertificateForFindAllWithPartOfDescriptionFilter();

            //when
            List<GiftCertificate> actual = certificateRepository
                    .findAll(null, null, "in the", null, null, 2, 2);

            //then
            assertEquals(expected, actual);
        }
    }

    @Test
    void checkAdd_shouldAddCertificateToDB() {
        //given
        GiftCertificate giftCertificateWithNullDates = DataStorage.findCertificateForAdd();
        Long expectedId = 11L;

        //when
        certificateRepository.add(giftCertificateWithNullDates);
        GiftCertificate actual = certificateRepository.findById(expectedId);

        //then
        assertEquals(actual.getId(), expectedId);
    }

    @Test
    void checkUpdate_shouldUpdateOnlyCertificateName() {
        //given
        GiftCertificate giftCertificateForUpdateName = DataStorage.findCertificateForUpdateName();
        String previousName = certificateRepository.findById(giftCertificateForUpdateName.getId()).getName();

        //when
        certificateRepository.update(giftCertificateForUpdateName);
        GiftCertificate actual = certificateRepository.findById(giftCertificateForUpdateName.getId());

        //then
        assertNotEquals(previousName, actual.getName());
        assertEquals("Hello", actual.getName());
    }

    @Test
    void checkUpdate_shouldUpdateOnlyCertificateDescription() {
        //given
        GiftCertificate giftCertificateForUpdateDescription = DataStorage.findCertificateForUpdateDescription();
        String previousDescription = certificateRepository.findById(giftCertificateForUpdateDescription.getId()).getDescription();

        //when
        certificateRepository.update(giftCertificateForUpdateDescription);
        GiftCertificate actual = certificateRepository.findById(giftCertificateForUpdateDescription.getId());

        //then
        assertNotEquals(previousDescription, actual.getName());
        assertEquals("Bye", actual.getDescription());
    }

    @Test
    void checkDelete_shouldDeleteCertificateWithId15AndThrowExceptionWhenTryingToGetDeletedCertificate() {
        //given
        Long id = 9L;

        //when
        certificateRepository.delete(id);

        //then
        assertThrows(RepositoryException.class, () -> certificateRepository.findById(id));
    }
}
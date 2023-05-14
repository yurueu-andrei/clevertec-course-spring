package ru.clevertec.ecl.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import ru.clevertec.ecl.entity.GiftCertificate;
import ru.clevertec.ecl.utils.TestUtils;

import java.util.ArrayList;
import java.util.List;

class CertificateRepositoryTest extends BaseRepositoryTest {

    @Autowired
    private CertificateRepository certificateRepository;

    @Test
    void findAllWithFilterTest_shouldReturnCertificatesWithPaginationAndWithoutFiltering() {
        //given
        List<GiftCertificate> expected = TestUtils.findCertsForFindAllWithoutFiltering();

        //when
        List<GiftCertificate> actual = certificateRepository.findAllWithFilter(
                null,
                null,
                null,
                PageRequest.of(0, 3));

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findAllWithFilterTest_shouldReturnCertificatesWithPaginationAndWithTagNameFiltering() {
        //given
        List<GiftCertificate> expected = TestUtils.findCertsForFindAllWithTagNameFiltering();

        //when
        List<GiftCertificate> actual = certificateRepository.findAllWithFilter(
                "extremal",
                null,
                null,
                PageRequest.of(0, 3));

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findAllWithFilterTest_shouldReturnCertificatesWithPaginationAndWithPartOfNameFiltering() {
        //given
        List<GiftCertificate> expected = TestUtils.findCertsForFindAllWithPartOfNameFiltering();

        //when
        List<GiftCertificate> actual = certificateRepository.findAllWithFilter(
                null,
                "individual",
                null,
                PageRequest.of(0, 3));

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findAllWithFilterTest_shouldReturnCertificatesWithPaginationAndWithPartOfDescriptionFiltering() {
        //given
        List<GiftCertificate> expected = TestUtils.findCertsForFindAllWithPartOfDescriptionFiltering();

        //when
        List<GiftCertificate> actual = certificateRepository.findAllWithFilter(
                null,
                null,
                "company",
                PageRequest.of(0, 2));

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findAllWithFilterTest_shouldReturnCertificatesWithPaginationAndWithCombinedFiltering() {
        //given
        List<GiftCertificate> expected = TestUtils.findCertsForFindAllWithCombinedFiltering();

        //when
        List<GiftCertificate> actual = certificateRepository.findAllWithFilter(
                "rest",
                "individual",
                "company",
                PageRequest.of(0, 3));

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findAllWithFilterTest_shouldReturnEmptyList() {
        //given
        List<GiftCertificate> expected = new ArrayList<>();

        //when
        List<GiftCertificate> actual = certificateRepository.findAllWithFilter(
                "unknown",
                "hello",
                "bye bye",
                PageRequest.of(0, 3));

        //then
        Assertions.assertEquals(expected, actual);
    }
}
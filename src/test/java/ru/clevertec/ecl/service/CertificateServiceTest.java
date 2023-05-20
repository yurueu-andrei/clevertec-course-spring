package ru.clevertec.ecl.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import ru.clevertec.ecl.dto.CertificateDto;
import ru.clevertec.ecl.dto.CertificateListDto;
import ru.clevertec.ecl.dto.CertificateRequestFilter;
import ru.clevertec.ecl.dto.CertificateSaveDto;
import ru.clevertec.ecl.dto.TagDto;
import ru.clevertec.ecl.dto.TagUpdateDto;
import ru.clevertec.ecl.entity.GiftCertificate;
import ru.clevertec.ecl.entity.Tag;
import ru.clevertec.ecl.exception.ServiceException;
import ru.clevertec.ecl.mapper.CertificateMapper;
import ru.clevertec.ecl.repository.CertificateRepository;
import ru.clevertec.ecl.repository.TagRepository;
import ru.clevertec.ecl.utils.TestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CertificateServiceTest {

    @Mock
    private CertificateRepository certificateRepository;

    @Mock
    private TagRepository tagRepository;

    @Spy
    private CertificateMapper certificateMapper = Mappers.getMapper(CertificateMapper.class);

    @InjectMocks
    private CertificateService certificateService;

    @Nested
    class FindById {

        @Test
        void findByIdTest_shouldReturnCertificateDtoWithId1() {
            //given
            Long id = 1L;
            GiftCertificate certificate = TestUtils.findCertificate();
            CertificateDto expected = TestUtils.findCertificateDto();

            //when
            when(certificateRepository.findById(id)).thenReturn(Optional.of(certificate));
            CertificateDto actual = certificateService.findById(id);

            //then
            Assertions.assertEquals(expected, actual);
        }

        @Test
        void findByIdTest_shouldThrowServiceExceptionForNonExistentCertificate() {
            //when
            when(certificateRepository.findById(11L)).thenReturn(Optional.empty());

            //then
            Assertions.assertThrows(ServiceException.class, () -> certificateService.findById(11L));
        }
    }

    @Nested
    class FindAll {

        @Test
        void findAllTest_shouldReturnAllCertificatesWithIdFrom1To2() {
            //given
            List<GiftCertificate> certificates = TestUtils.findCertificates();
            List<CertificateListDto> expected = TestUtils.findListOfCertificateListDto();
            CertificateRequestFilter requestFilter = new CertificateRequestFilter();
            requestFilter.setPageable(PageRequest.of(0, 2));

            //when
            when(certificateRepository.findAllWithFilter(
                    null,
                    null,
                    null,
                    PageRequest.of(0, 2)))
                    .thenReturn(certificates);
            List<CertificateListDto> actual = certificateService.findAll(requestFilter);

            //then
            Assertions.assertEquals(expected, actual);
        }

        @Test
        void findAllTest_shouldReturnAllCertificatesWithFilteringByTagName() {
            //given
            List<GiftCertificate> certificates = TestUtils.findCertificates();
            List<CertificateListDto> expected = TestUtils.findListOfCertificateListDto();
            CertificateRequestFilter requestFilter = new CertificateRequestFilter();
            requestFilter.setPageable(PageRequest.of(0, 2));
            requestFilter.setTagName("extremal");

            //when
            when(certificateRepository.findAllWithFilter(
                    "extremal",
                    null,
                    null,
                    PageRequest.of(0, 2)))
                    .thenReturn(certificates);
            List<CertificateListDto> actual = certificateService.findAll(requestFilter);

            //then
            Assertions.assertEquals(expected, actual);
        }

        @Test
        void findAllTest_shouldReturnAllCertificatesWithPartOfNameIndividualAndCreateDateDescendingOrder() {
            //given
            List<GiftCertificate> certificates = TestUtils.findCertificates();
            List<CertificateListDto> expected = TestUtils.findListOfCertificateListDto();
            CertificateRequestFilter requestFilter = new CertificateRequestFilter();
            requestFilter.setPageable(PageRequest.of(0, 2, Sort.by("createDate").descending()));
            requestFilter.setPartOfName("individual");

            //when
            when(certificateRepository.findAllWithFilter(
                    null,
                    "individual",
                    null,
                    PageRequest.of(0, 2, Sort.by("createDate").descending())))
                    .thenReturn(certificates);
            List<CertificateListDto> actual = certificateService.findAll(requestFilter);

            //then
            Assertions.assertEquals(expected, actual);
        }
    }

    @Nested
    class Delete {

        @Test
        void deleteTest_shouldReturnTrueInCaseOfSuccessfulDelete() {
            //given
            Long id = 3L;
            GiftCertificate certificate = TestUtils.getCertificate(3);

            //when
            when(certificateRepository.findById(id)).thenReturn(Optional.of(certificate));
            boolean actual = certificateService.delete(id);

            //then
            Assertions.assertTrue(actual);
        }

        @Test
        void deleteTest_shouldThrowServiceExceptionInCaseOfUnsuccessfulDelete() {
            //given
            Long id = 55L;

            //when
            when(certificateRepository.findById(id)).thenReturn(Optional.empty());

            //then
            Assertions.assertThrows(ServiceException.class, () -> certificateService.delete(id));
        }
    }
}
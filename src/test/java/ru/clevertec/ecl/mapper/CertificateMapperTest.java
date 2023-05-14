package ru.clevertec.ecl.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.clevertec.ecl.dto.CertificateDto;
import ru.clevertec.ecl.dto.CertificateListDto;
import ru.clevertec.ecl.dto.CertificateSaveDto;
import ru.clevertec.ecl.entity.GiftCertificate;
import ru.clevertec.ecl.utils.TestUtils;

import java.util.List;

class CertificateMapperTest {

    private CertificateMapper certificateMapper;

    @BeforeEach
    void setUp() {
        this.certificateMapper = Mappers.getMapper(CertificateMapper.class);
    }

    @Test
    void toDtoTest_shouldTransferGiftCertificateObjectToCertificateDto() {
        //given
        CertificateDto expected = TestUtils.findCertificateDtoForCertificateMapperTest();
        GiftCertificate certificate = TestUtils.findCertificateForCertificateMapperTest();

        //when
        CertificateDto actual = certificateMapper.toDto(certificate);

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void toListDtoTest_shouldTransferListOfGiftCertificateObjectsToListOfCertificateListDto() {
        //given
        List<CertificateListDto> expected = TestUtils.findListOfCertificateListDtoForCertificateMapperTest();
        List<GiftCertificate> certificates = TestUtils.findCertificatesForCertificateMapperTest();

        //when
        List<CertificateListDto> actual = certificateMapper.toListDto(certificates);

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void fromSaveDtoTest_shouldTransferCertificateSaveDtoToGiftCertificateObject() {
        //given
        GiftCertificate expected = TestUtils.findCertificateForCertificateMapperFromSaveDtoTest();
        CertificateSaveDto certificateDto = TestUtils.findCertificateSaveDtoForCertificateMapperTest();

        //when
        GiftCertificate actual = certificateMapper.fromSaveDto(certificateDto);

        //then
        Assertions.assertEquals(expected, actual);
    }
}
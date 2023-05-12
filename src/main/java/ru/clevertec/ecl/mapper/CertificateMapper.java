package ru.clevertec.ecl.mapper;

import org.mapstruct.Mapper;
import ru.clevertec.ecl.dto.CertificateDto;
import ru.clevertec.ecl.dto.CertificateListDto;
import ru.clevertec.ecl.dto.CertificateSaveDto;
import ru.clevertec.ecl.entity.GiftCertificate;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CertificateMapper {

    CertificateDto toDto(GiftCertificate giftCertificate);

    List<CertificateListDto> toListDto(List<GiftCertificate> giftCertificates);

    GiftCertificate fromSaveDto(CertificateSaveDto certificateSaveDto);
}

package ru.clevertec.ecl.mapper;

import org.mapstruct.Mapper;
import ru.clevertec.ecl.dto.CertificateDto;
import ru.clevertec.ecl.dto.CertificateListDto;
import ru.clevertec.ecl.dto.CertificateSaveDto;
import ru.clevertec.ecl.dto.CertificateUpdateDto;
import ru.clevertec.ecl.entity.GiftCertificate;

import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring")
public interface CertificateMapper {
    CertificateDto toDto(GiftCertificate giftCertificate);
    List<CertificateListDto> toListDto(List<GiftCertificate> giftCertificates);
    default GiftCertificate fromSaveDto(CertificateSaveDto certificateSaveDto) {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setName(certificateSaveDto.getName());
        giftCertificate.setDescription(certificateSaveDto.getDescription());
        giftCertificate.setPrice(certificateSaveDto.getPrice());
        giftCertificate.setDuration(certificateSaveDto.getDuration());
        giftCertificate.setCreateDate(LocalDateTime.now());
        return giftCertificate;
    }

    default GiftCertificate fromUpdateDto(CertificateUpdateDto certificateUpdateDto) {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(certificateUpdateDto.getId());
        giftCertificate.setName(certificateUpdateDto.getName());
        giftCertificate.setDescription(certificateUpdateDto.getDescription());
        giftCertificate.setPrice(certificateUpdateDto.getPrice());
        giftCertificate.setDuration(certificateUpdateDto.getDuration());
        giftCertificate.setLastUpdateDate(LocalDateTime.now());
        return giftCertificate;
    }
}

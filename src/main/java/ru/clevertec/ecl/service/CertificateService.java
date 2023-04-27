package ru.clevertec.ecl.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.clevertec.ecl.dto.CertificateDto;
import ru.clevertec.ecl.dto.CertificateListDto;
import ru.clevertec.ecl.dto.CertificateSaveDto;
import ru.clevertec.ecl.dto.CertificateUpdateDto;
import ru.clevertec.ecl.entity.GiftCertificate;
import ru.clevertec.ecl.entity.Tag;
import ru.clevertec.ecl.exception.ServiceException;
import ru.clevertec.ecl.mapper.CertificateMapper;
import ru.clevertec.ecl.repository.CertificateRepository;
import ru.clevertec.ecl.repository.TagRepository;

import java.util.List;

/**
 * Service class for certificates with <b>CRUD</b> operations and mapping into <b>DTO</b>.
 *
 * @author Yuryeu Andrei
 */
@Service
@RequiredArgsConstructor
public class CertificateService {
    private final CertificateRepository certificateRepository;
    private final TagRepository tagRepository;
    private final CertificateMapper certificateMapper;

    public CertificateDto findById(Long id) throws ServiceException {
        try {
            return certificateMapper.toDto(certificateRepository.findById(id));
        } catch (Exception ex) {
            throw new ServiceException("Certificate with id = " + id + " was not found");
        }
    }

    public List<CertificateListDto> findAll(
            String tagName,
            String partOfName,
            String partOfDescription,
            String dateSortingOrder,
            String nameSortingOrder,
            int limit,
            int offset
    ) throws ServiceException {
        try {
            return certificateMapper.toListDto(certificateRepository.findAll(
                    tagName,
                    partOfName,
                    partOfDescription,
                    dateSortingOrder,
                    nameSortingOrder,
                    limit, offset)
            );
        } catch (Exception ex) {
            throw new ServiceException("Certificates were not found");
        }
    }

    public CertificateDto add(CertificateSaveDto certificateSaveDto) throws ServiceException {
        try {
            GiftCertificate giftCertificate = certificateMapper.fromSaveDto(certificateSaveDto);
            if (certificateSaveDto.getTags() != null) {
                addTagsToCertificate(giftCertificate, certificateSaveDto.getTags());
            }

            return certificateMapper.toDto(certificateRepository.add(giftCertificate));
        } catch (Exception ex) {
            throw new ServiceException("Certificate was not added");
        }
    }

    public boolean update(CertificateUpdateDto certificateUpdateDto) throws ServiceException {
        try {
            GiftCertificate giftCertificate = certificateMapper.fromUpdateDto(certificateUpdateDto);
            if (certificateUpdateDto.getTags() != null) {
                addTagsToCertificate(giftCertificate, certificateUpdateDto.getTags());
            }

            return certificateRepository.update(giftCertificate);
        } catch (Exception ex) {
            throw new ServiceException("Certificate with id = " + certificateUpdateDto.getId() + " was not updated");
        }
    }

    /**
     * Service private method, which checks if tag is already in DB, then it just add it to the certificate,
     * but if it's not, it adds tag to db and then adds to certificate(mapstruct mapper does not map tags!)
     */
    private void addTagsToCertificate(GiftCertificate giftCertificate, List<String> tagNames) {
        for (String tagName : tagNames) {
            Tag tag = tagRepository.findByName(tagName);
            if (tag != null) {
                giftCertificate.getTags().add(tag);
            } else {
                Tag addedTag = tagRepository.add(new Tag().setName(tagName));
                giftCertificate.getTags().add(addedTag);
            }
        }
    }

    public boolean delete(Long id) {
        try {
            return certificateRepository.delete(id);
        } catch (Exception ex) {
            throw new ServiceException("Certificate with id = " + id + " was not deleted");
        }
    }
}

package ru.clevertec.ecl.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.ecl.dto.CertificateDto;
import ru.clevertec.ecl.dto.CertificateListDto;
import ru.clevertec.ecl.dto.CertificateRequestFilter;
import ru.clevertec.ecl.dto.CertificateSaveDto;
import ru.clevertec.ecl.dto.CertificateUpdateDto;
import ru.clevertec.ecl.dto.TagDto;
import ru.clevertec.ecl.entity.GiftCertificate;
import ru.clevertec.ecl.entity.Tag;
import ru.clevertec.ecl.exception.ServiceException;
import ru.clevertec.ecl.mapper.CertificateMapper;
import ru.clevertec.ecl.repository.CertificateRepository;
import ru.clevertec.ecl.repository.TagRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @Transactional(readOnly = true)
    public CertificateDto findById(Long id) throws ServiceException {
        return certificateRepository.findById(id).map(certificateMapper::toDto)
                .orElseThrow(() -> new ServiceException("Certificate with id = " + id + " was not found"));
    }

    @Transactional(readOnly = true)
    public List<CertificateListDto> findAll(CertificateRequestFilter filter) throws ServiceException {
        try {
            return certificateMapper.toListDto(
                    certificateRepository.findAllWithFilter(
                            filter.getTagName(),
                            filter.getPartOfName(),
                            filter.getPartOfDescription(),
                            filter.getPageable()
                    ));
        } catch (Exception ex) {
            throw new ServiceException("Certificates were not found");
        }
    }

    @Transactional
    public CertificateDto add(CertificateSaveDto certificateSaveDto) throws ServiceException {
        try {
            GiftCertificate certificate = certificateMapper.fromSaveDto(certificateSaveDto);
            if (certificateSaveDto.getTags().size() > 0) {
                certificate.setTags(constructTags(certificate.getTags()));
            }
            return certificateMapper.toDto(certificateRepository.save(certificate));
        } catch (Exception ex) {
            throw new ServiceException("Certificate was not added");
        }
    }

    /**
     * Service private method, which checks if tag is already in DB, then it just add it to the certificate,
     * but if it's not, it adds tag to db and then adds to certificate(mapstruct mapper does not map tags!)
     */
    private Set<Tag> constructTags(Collection<Tag> tags) {
        List<String> allTagsNames = tags.stream().map(Tag::getName).toList();
        List<Tag> existingTags = tagRepository.findAllByNameIn(allTagsNames);
        List<String> existingTagsNames = existingTags.stream().map(Tag::getName).toList();
        List<Tag> notExistingTags = tags.stream().filter(tag -> !existingTagsNames.contains(tag.getName())).toList();
        existingTags.addAll(notExistingTags);
        return new HashSet<>(existingTags);
    }

    @Transactional
    public boolean update(CertificateUpdateDto certificateUpdateDto) throws ServiceException {
        try {
            GiftCertificate certificate = certificateRepository.findById(certificateUpdateDto.getId())
                    .orElseThrow(() ->
                            new ServiceException(
                                    String.format(
                                            "The certificate was not updated. The certificate was not found. id = %d",
                                            certificateUpdateDto.getId())));

            settingUpdatedFields(certificate, certificateUpdateDto);
            certificateRepository.flush();
            return true;
        } catch (Exception ex) {
            throw new ServiceException("Certificate with id = " + certificateUpdateDto.getId() + " was not updated");
        }
    }

    private void settingUpdatedFields(
            GiftCertificate certificate,
            CertificateUpdateDto certificateUpdateDto
    ) {
        if (certificateUpdateDto.getName() != null) {
            certificate.setName(certificateUpdateDto.getName());
        }

        if (certificateUpdateDto.getDescription() != null) {
            certificate.setDescription(certificateUpdateDto.getDescription());
        }

        if (certificateUpdateDto.getDuration() != null) {
            certificate.setDuration(certificateUpdateDto.getDuration());
        }

        if (certificateUpdateDto.getPrice() != null) {
            certificate.setPrice(certificateUpdateDto.getPrice());
        }

        if (certificateUpdateDto.getTags() != null) {
            List<String> certificateTagsNames = certificate.getTags().stream().map(Tag::getName).toList();
            List<String> dtoTagsNames = new ArrayList<>(certificateUpdateDto.getTags().stream().map(TagDto::getName).toList());
            dtoTagsNames.removeAll(certificateTagsNames);
            List<Tag> tagsAbsentInCertificate = dtoTagsNames.stream().map(name -> new Tag().setName(name)).toList();
            certificate.getTags().addAll(constructTags(tagsAbsentInCertificate));
        }
    }

    @Transactional
    public boolean delete(Long id) {
        GiftCertificate certificate = certificateRepository.findById(id)
                .orElseThrow(() ->
                        new ServiceException(
                                String.format(
                                        "The certificate was not deleted. The certificate was not found. id = %d", id)));
        try {
            certificateRepository.delete(certificate);
            return true;
        } catch (Exception ex) {
            throw new ServiceException("Certificate with id = " + id + " was not deleted");
        }
    }
}

package ru.clevertec.ecl.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.ecl.dto.CertificateDto;
import ru.clevertec.ecl.dto.CertificateListDto;
import ru.clevertec.ecl.dto.CertificateRequestFilter;
import ru.clevertec.ecl.dto.CertificateSaveDto;
import ru.clevertec.ecl.dto.CertificateUpdateDto;
import ru.clevertec.ecl.exception.ServiceException;
import ru.clevertec.ecl.service.CertificateService;

import java.util.List;

/**
 * Controller for certificates with <b>CRUD</b> operations
 *
 * @author Yuryeu Andrei
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/certificates")
public class CertificateController {

    private final CertificateService certificateService;

    /**
     * Endpoint, which finds the Certificate by id given in the URL and return it as CertificateDto
     *
     * @param id ID of target entity
     * @return returns <b>CertificateDto</b> made out of found Certificate
     * @see CertificateDto
     */
    @GetMapping("/{id}")
    public ResponseEntity<CertificateDto> findById(
            @PathVariable Long id
    ) throws ServiceException {
        return new ResponseEntity<>(certificateService.findById(id), HttpStatus.OK);
    }

    /**
     * Endpoint, which finds all Certificates and return it as CertificateListDtos
     *
     * @param tagName           used for filtering(optional), defines the Tag name,
     *                          which must be present in resulting CertificateListDtos
     * @param partOfName        used for filtering(optional), defines part of certificate name,
     *                          which must be present in resulting CertificateListDtos
     * @param partOfDescription used for filtering(optional), defines part of certificate description,
     *                          which must be present in resulting CertificateListDtos
     * @param pageable          used for pagination, defines the number of tags on the page and the number of page
     * @return returns <b>CertificateListDtos</b> made out of found Certificates
     * @see CertificateListDto
     */
    @GetMapping
    public ResponseEntity<List<CertificateListDto>> findAll(
            @RequestParam(required = false) String tagName,
            @RequestParam(required = false) String partOfName,
            @RequestParam(required = false) String partOfDescription,
            Pageable pageable
    ) throws ServiceException {
        return new ResponseEntity<>(certificateService.findAll(
                new CertificateRequestFilter(
                        tagName,
                        partOfName,
                        partOfDescription,
                        pageable
                )), HttpStatus.OK);
    }

    /**
     * Endpoint, which gets CertificateDto and creates Certificate
     *
     * @param certificateSaveDto contains all the fields needed to create Certificate
     * @return returns created entity as <b>CertificateDto</b>
     * @see CertificateSaveDto
     */
    @PostMapping
    public ResponseEntity<CertificateDto> add(
            @RequestBody CertificateSaveDto certificateSaveDto
    ) throws ServiceException {
        return new ResponseEntity<>(certificateService.add(certificateSaveDto), HttpStatus.OK);
    }

    /**
     * Endpoint, which updates existing Certificate
     *
     * @param certificateUpdateDto contains id and the fields to be updated
     * @return returns <b>boolean</b> value (only true in case of successful operation)
     * @throws ServiceException in case of failure
     * @see CertificateUpdateDto
     */
    @PutMapping
    public boolean update(
            @RequestBody CertificateUpdateDto certificateUpdateDto
    ) throws ServiceException {
        return certificateService.update(certificateUpdateDto);
    }

    /**
     * Endpoint, which deletes existing Certificate
     *
     * @param id id of the entity to be deleted
     * @return returns <b>boolean</b> value (only true in case of successful operation)
     * @throws ServiceException in case of failure
     */
    @DeleteMapping("/{id}")
    public boolean delete(
            @PathVariable Long id
    ) throws ServiceException {
        return certificateService.delete(id);
    }
}

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
import ru.clevertec.ecl.dto.TagDto;
import ru.clevertec.ecl.dto.TagUpdateDto;
import ru.clevertec.ecl.exception.ServiceException;
import ru.clevertec.ecl.service.TagService;

import java.util.List;

/**
 * Controller for tags with <b>CRUD</b> operations
 *
 * @author Yuryeu Andrei
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/tags")
public class TagController {

    private final TagService tagService;

    /**
     * Endpoint, which finds the Tag by id given in the URL and return it as TagDto
     *
     * @param id ID of target entity
     * @return returns <b>TagDto</b> made out of found Tag
     * @see TagDto
     */
    @GetMapping("/{id}")
    public ResponseEntity<TagDto> findById(
            @PathVariable Long id
    ) throws ServiceException {
        return new ResponseEntity<>(tagService.findById(id), HttpStatus.OK);
    }

    /**
     * Endpoint, which finds all Tags and return it as TagDtos
     *
     * @param pageNumber used for pagination, defines the number of the page
     * @param pageSize   used for pagination, defines the number of tags on the page
     * @return returns <b>TagDtos</b> made out of found Tags
     * @see TagDto
     */
    @GetMapping
    public ResponseEntity<List<TagDto>> findAll(
            @RequestParam("size") int pageSize,
            @RequestParam("page") int pageNumber,
            Pageable pageable
    ) throws ServiceException {
        return new ResponseEntity<>(tagService.findAll(pageable), HttpStatus.OK);
    }

    /**
     * Endpoint, which gets TagDto and creates Tag
     *
     * @param tagDto contains all the fields of Tag except id
     * @return returns created entity as <b>TagDto</b>
     * @see TagDto
     */
    @PostMapping
    public ResponseEntity<TagDto> add(
            @RequestBody TagDto tagDto
    ) throws ServiceException {
        return new ResponseEntity<>(tagService.add(tagDto), HttpStatus.OK);
    }

    /**
     * Endpoint, which updates existing Tag
     *
     * @param tagUpdateDto contains id and the fields to be updated
     * @return returns <b>boolean</b> value (only true in case of successful operation)
     * @throws ServiceException in case of failure
     * @see TagUpdateDto
     */
    @PutMapping
    public boolean update(
            @RequestBody TagUpdateDto tagUpdateDto
    ) throws ServiceException {
        return tagService.update(tagUpdateDto);
    }

    /**
     * Endpoint, which deletes existing Tag
     *
     * @param id id of the entity to be deleted
     * @return returns <b>boolean</b> value (only true in case of successful operation)
     * @throws ServiceException in case of failure
     */
    @DeleteMapping("/{id}")
    public boolean delete(
            @PathVariable Long id
    ) throws ServiceException {
        return tagService.delete(id);
    }

    /**
     * Endpoint, which finds the most used Tag of user with the highest cost of all orders and return it as TagDto
     *
     * @return returns <b>TagDto</b> made out of found Tag
     * @see TagDto
     */
    @GetMapping("/mostUsed")
    public ResponseEntity<TagDto> findTheMostUsedTag() throws ServiceException {
        return new ResponseEntity<>(tagService.findTheMostUsedTag(), HttpStatus.OK);
    }
}

package ru.clevertec.ecl.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.clevertec.ecl.dto.TagDto;
import ru.clevertec.ecl.dto.TagUpdateDto;
import ru.clevertec.ecl.entity.Tag;
import ru.clevertec.ecl.exception.ServiceException;
import ru.clevertec.ecl.mapper.TagMapper;
import ru.clevertec.ecl.repository.TagRepository;

import java.util.List;

/**
 * Service class for tags with <b>CRUD</b> operations and mapping into <b>DTO</b>.
 *
 * @author Yuryeu Andrei
 */
@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    public TagDto findById(Long id) throws ServiceException {
        try {
            return tagMapper.toDto(tagRepository.findById(id));
        } catch (Exception ex) {
            throw new ServiceException("Tag with id = " + id + " was not found");
        }
    }

    public List<TagDto> findAll(int limit, int offset) throws ServiceException {
        try {
            return tagMapper.toDto(tagRepository.findAll(limit, offset));
        } catch (Exception ex) {
            throw new ServiceException("Tags were not found");
        }
    }

    public TagDto add(TagDto tagDto) throws ServiceException {
        try {
            Tag tag = tagMapper.fromDto(tagDto);
            return tagMapper.toDto(tagRepository.add(tag));
        } catch (Exception ex) {
            throw new ServiceException("Tag was not added");
        }
    }

    public boolean update(TagUpdateDto tagUpdateDto) throws ServiceException {
        try {
            return tagRepository.update(tagMapper.fromUpdateDto(tagUpdateDto));
        } catch (Exception ex) {
            throw new ServiceException("Tag with id = " + tagUpdateDto.getId() + " was not updated");
        }
    }

    public boolean delete(Long id) {
        try {
            return tagRepository.delete(id);
        } catch (Exception ex) {
            throw new ServiceException("Tag with id = " + id + " was not deleted");
        }
    }
}

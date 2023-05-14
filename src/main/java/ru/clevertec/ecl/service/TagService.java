package ru.clevertec.ecl.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.ecl.dto.TagDto;
import ru.clevertec.ecl.dto.TagUpdateDto;
import ru.clevertec.ecl.entity.Tag;
import ru.clevertec.ecl.entity.User;
import ru.clevertec.ecl.exception.ServiceException;
import ru.clevertec.ecl.mapper.TagMapper;
import ru.clevertec.ecl.repository.TagRepository;
import ru.clevertec.ecl.repository.UserRepository;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Service class for tags with <b>CRUD</b> operations and mapping into <b>DTO</b>.
 *
 * @author Yuryeu Andrei
 */
@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;
    private final UserRepository userRepository;
    private final TagMapper tagMapper;

    public TagDto findById(Long id) throws ServiceException {
        return tagRepository.findById(id).map(tagMapper::toDto)
                .orElseThrow(() -> new ServiceException("Tag with id = " + id + " was not found"));
    }

    public List<TagDto> findAll(Pageable pageable) throws ServiceException {
        try {
            List<Tag> list = tagRepository.findAll(pageable).toList();
            return tagMapper.toDto(list);
        } catch (Exception ex) {
            throw new ServiceException("Tags were not found");
        }
    }

    @Transactional
    public TagDto add(TagDto tagDto) throws ServiceException {
        try {
            Tag tag = tagMapper.fromDto(tagDto);
            return tagMapper.toDto(tagRepository.save(tag));
        } catch (Exception ex) {
            throw new ServiceException("Tag was not added");
        }
    }

    @Transactional
    public boolean update(TagUpdateDto tagUpdateDto) throws ServiceException {
        Tag tag = tagRepository.findById(tagUpdateDto.getId())
                .orElseThrow(() ->
                        new ServiceException(
                                String.format(
                                        "The tag was not updated. The tag was not found. id = %d",
                                        tagUpdateDto.getId())));
        try {
            tag.setName(tagUpdateDto.getName());
            tagRepository.flush();
            return true;
        } catch (Exception ex) {
            throw new ServiceException("Tag with id = " + tagUpdateDto.getId() + " was not updated");
        }
    }

    @Transactional
    public boolean delete(Long id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() ->
                        new ServiceException(
                                String.format(
                                        "The tag was not deleted. The tag was not found. id = %d", id)));
        try {
            tagRepository.delete(tag);
            return true;
        } catch (Exception ex) {
            throw new ServiceException("Tag with id = " + id + " was not deleted");
        }
    }

    @Transactional
    public TagDto findTheMostUsedTag() {
        Long id = userRepository.findIdOfUserWithTheHighestCostOfAllOrders(PageRequest.of(0, 1)).get(0);
        User user = userRepository.findById(id).orElseThrow(() -> new ServiceException("User not found"));
        return tagMapper.toDto(user.getOrders().stream()
                .flatMap(order -> order.getCertificate().getTags().stream())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null));
    }
}

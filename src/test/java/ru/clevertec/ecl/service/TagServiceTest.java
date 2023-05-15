package ru.clevertec.ecl.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.dto.TagDto;
import ru.clevertec.ecl.dto.TagUpdateDto;
import ru.clevertec.ecl.dto.UserDto;
import ru.clevertec.ecl.dto.UserListDto;
import ru.clevertec.ecl.dto.UserSaveDto;
import ru.clevertec.ecl.dto.UserUpdateDto;
import ru.clevertec.ecl.entity.Tag;
import ru.clevertec.ecl.entity.User;
import ru.clevertec.ecl.exception.ServiceException;
import ru.clevertec.ecl.mapper.TagMapper;
import ru.clevertec.ecl.repository.TagRepository;
import ru.clevertec.ecl.repository.UserRepository;
import ru.clevertec.ecl.utils.TestUtils;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TagServiceTest {

    @Mock
    private TagRepository tagRepository;

    @Mock
    private UserRepository userRepository;

    @Spy
    private TagMapper tagMapper = Mappers.getMapper(TagMapper.class);

    @InjectMocks
    private TagService tagService;

    @Nested
    class FindById {

        @Test
        void findByIdTest_shouldReturnTagDtoWithId1() {
            //given
            Long id = 1L;
            Tag tag = TestUtils.getTag(1);
            TagDto expected = new TagDto(tag.getName());

            //when
            when(tagRepository.findById(id)).thenReturn(Optional.of(tag));
            TagDto actual = tagService.findById(id);

            //then
            Assertions.assertEquals(expected, actual);
        }

        @Test
        void findByIdTest_shouldThrowServiceExceptionForNonExistentTag() {
            //when
            when(tagRepository.findById(55L)).thenReturn(Optional.empty());

            //then
            Assertions.assertThrows(ServiceException.class, () -> tagService.findById(55L));
        }
    }

    @Test
    void findAllTest_shouldReturnAllTagWithIdFrom4To6() {
        //given
        Pageable pageable = PageRequest.of(1, 3);
        List<Tag> tags = List.of(TestUtils.getTag(4), TestUtils.getTag(5), TestUtils.getTag(6));
        List<TagDto> expected = tags.stream().map(tag -> new TagDto(tag.getName())).toList();

        //when
        when(tagRepository.findAll(pageable)).thenReturn(new PageImpl<>(tags));
        List<TagDto> actual = tagService.findAll(pageable);

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void addTest_shouldAddTagAndReturnTagDto() {
        //given
        Tag tagWithoutId = new Tag(null, "hello");
        Tag tagWithId = new Tag(20L, "hello");
        TagDto expected = new TagDto("hello");

        //when
        when(tagRepository.save(tagWithoutId)).thenReturn(tagWithId);
        TagDto actual = tagService.add(expected);

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Nested
    class Update {

        @Test
        void updateTest_shouldUpdateTagAndReturnTrueInCaseOfSuccessfulUpdate() {
            //given
            Long id = 3L;
            Tag tag = TestUtils.getTag(3);
            TagUpdateDto tagUpdateDto = new TagUpdateDto();
            tagUpdateDto.setId(id);
            tagUpdateDto.setName("Bye bye");
            //when
            when(tagRepository.findById(id)).thenReturn(Optional.of(tag));
            boolean actual = tagService.update(tagUpdateDto);

            //then
            Assertions.assertTrue(actual);
        }

        @Test
        void updateTest_shouldThrowServiceExceptionInCaseOfUnsuccessfulUpdate() {
            //given
            Long id = 3L;
            TagUpdateDto tagUpdateDto = new TagUpdateDto();
            tagUpdateDto.setId(id);
            tagUpdateDto.setName("Hi hi");
            //when
            when(tagRepository.findById(id)).thenReturn(Optional.empty());

            //then
            Assertions.assertThrows(ServiceException.class, () -> tagService.update(tagUpdateDto));
        }
    }

    @Nested
    class Delete {

        @Test
        void deleteTest_shouldReturnTrueInCaseOfSuccessfulDelete() {
            //given
            Long id = 3L;
            Tag tag = TestUtils.getTag(3);

            //when
            when(tagRepository.findById(id)).thenReturn(Optional.of(tag));
            boolean actual = tagService.delete(id);

            //then
            Assertions.assertTrue(actual);
        }

        @Test
        void deleteTest_shouldThrowServiceExceptionInCaseOfUnsuccessfulDelete() {
            //given
            Long id = 55L;

            //when
            when(tagRepository.findById(id)).thenReturn(Optional.empty());

            //then
            Assertions.assertThrows(ServiceException.class, () -> tagService.delete(id));
        }
    }
}
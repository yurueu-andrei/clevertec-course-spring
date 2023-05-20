package ru.clevertec.ecl.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.clevertec.ecl.dto.TagDto;
import ru.clevertec.ecl.entity.Tag;
import ru.clevertec.ecl.utils.TestUtils;

import java.util.List;

class TagMapperTest {

    private TagMapper tagMapper;

    @BeforeEach
    void setUp() {
        this.tagMapper = Mappers.getMapper(TagMapper.class);
    }

    @Test
    void toDtoTest_shouldTransferTagObjectToTagDto() {
        //given
        TagDto expected = new TagDto("entertainment");
        Tag tag = TestUtils.findTagForTagMapperTest();

        //when
        TagDto actual = tagMapper.toDto(tag);

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void toDtoTest_shouldTransferListOfTagsTagsToListOfTagDto() {
        //given
        List<TagDto> expected = List.of(
                new TagDto("entertainment"),
                new TagDto("health"),
                new TagDto("driving"));
        List<Tag> tags = TestUtils.findTags();

        //when
        List<TagDto> actual = tagMapper.toDto(tags);

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void toDtoTest_shouldTransferTagDtoToTagObject() {
        //given
        TagDto tagDto = new TagDto("entertainment");
        Tag expected = TestUtils.findTagForTagMapperTest();
        expected.setId(null);

        //when
        Tag actual = tagMapper.fromDto(tagDto);

        //then
        Assertions.assertEquals(expected, actual);
    }
}
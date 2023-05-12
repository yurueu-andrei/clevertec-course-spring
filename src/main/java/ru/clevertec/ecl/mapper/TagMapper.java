package ru.clevertec.ecl.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.clevertec.ecl.dto.TagDto;
import ru.clevertec.ecl.entity.Tag;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TagMapper {

    TagDto toDto(Tag tag);

    List<TagDto> toDto(List<Tag> tags);

    Tag fromDto(TagDto tagDto);
}

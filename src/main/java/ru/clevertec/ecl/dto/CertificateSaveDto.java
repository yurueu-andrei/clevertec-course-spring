package ru.clevertec.ecl.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CertificateSaveDto {

    private String name;
    private String description;
    private BigDecimal price;
    private Integer duration;
    private List<TagDto> tags;
}

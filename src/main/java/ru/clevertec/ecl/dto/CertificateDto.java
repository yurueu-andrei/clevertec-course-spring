package ru.clevertec.ecl.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CertificateDto {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private int duration;
    private LocalDateTime createDate;
    private List<TagDto> tags;
}

package ru.clevertec.ecl.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderListDto {
    private Long id;
    private LocalDateTime createDate;
    private BigDecimal cost;
    private CertificateDto certificate;
}

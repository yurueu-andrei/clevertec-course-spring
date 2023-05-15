package ru.clevertec.ecl.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CertificateRequestFilter {

    private String tagName;
    private String partOfName;
    private String partOfDescription;
    private Pageable pageable;
}

package ru.clevertec.ecl.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CertificateRequestFilter {

    private String tagName;
    private String partOfName;
    private String partOfDescription;
    private String dateSortingOrder;
    private String nameSortingOrder;
    private int pageSize;
    private int pageNumber;
}

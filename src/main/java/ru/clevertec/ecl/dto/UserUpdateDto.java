package ru.clevertec.ecl.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDto {

    private Long id;
    private String firstName;
    private String lastName;
    private Integer age;
    private String email;
    private String password;
}

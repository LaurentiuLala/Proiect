package com.example.proiect1.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRegisterDTO {
    private String name;
    private String lastName;
    private String email;
    private String password;
}

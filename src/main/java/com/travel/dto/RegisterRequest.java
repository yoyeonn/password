package com.travel.dto;

import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
@Getter
@Setter
public class RegisterRequest {
    @NotBlank
    private String name;

    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String password;
}

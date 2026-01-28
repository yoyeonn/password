package com.travel.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserMeResponse {
    private Long id;
    private String name;
    private String email;
    private String role;
}

package com.example.vertsysSecond.models;

import lombok.Data;
@Data
public class Profile {
    //@NotBlank (message = "Firstname must be specified!")
    private String firstName;
    //@NotBlank (message = "Lastname must be specified!")
    private String lastName;
    private String email;
    private Long id;
    private Long accountId;
}

package com.example.vertsysSecond.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Account {
    private Long id;
    @JsonProperty("userName")
    private String userName;
    @JsonProperty("password")
    private String password;
}

package com.example.vertsysSecond.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Account {
    private Long id;
    //@NotBlank
   // @Pattern(regexp="[a-zA-Z0-9]{3,20}")
    @JsonProperty("userName")
    private String userName;
    //@NotBlank
    @JsonProperty("password")
    //@Pattern(regexp="[a-zA-Z0-9]{1,20}")
    private String password;
}

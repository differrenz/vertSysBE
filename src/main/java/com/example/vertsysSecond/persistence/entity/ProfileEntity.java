package com.example.vertsysSecond.persistence.entity;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
@Data
public class ProfileEntity {
    private String firstName;
    private String lastName;
    private String email;
    @GeneratedValue
    @Id
    private Long id;
    @OneToOne
    private AccountEntity account;
}

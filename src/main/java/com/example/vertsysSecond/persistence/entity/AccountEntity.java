package com.example.vertsysSecond.persistence.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class AccountEntity {
    @Column(unique = true)
    private String userName;
    private String password;
    @Id
    @GeneratedValue
    private Long id;
}

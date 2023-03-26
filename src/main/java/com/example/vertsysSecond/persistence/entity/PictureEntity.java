package com.example.vertsysSecond.persistence.entity;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class PictureEntity {
    private String type;

    @Id
    @GeneratedValue
    private Long id;

    private String namePicture;
    @ManyToOne
    private ProfileEntity profileEntity;

    @Lob
    @Column(length = 1000000)
    private byte[] picByte;
}

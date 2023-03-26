package com.example.vertsysSecond.persistence.repository;

import com.example.vertsysSecond.persistence.entity.PictureEntity;
import com.example.vertsysSecond.persistence.entity.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PictureRepository extends JpaRepository<PictureEntity, Long> {
    void deletePictureEntityByProfileEntity(ProfileEntity profileEntity);

    PictureEntity findByProfileEntity_Id(Long id);

}



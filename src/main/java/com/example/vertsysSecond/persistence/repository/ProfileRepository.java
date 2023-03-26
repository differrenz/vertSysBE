package com.example.vertsysSecond.persistence.repository;


import com.example.vertsysSecond.persistence.entity.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotNull;
import java.util.Optional;

public interface ProfileRepository extends JpaRepository<ProfileEntity, Long> {
    void deleteProfileEntityByAccount_Id(Long id);

    ProfileEntity findProfileEntityByAccount_Id(Long id);

    ProfileEntity findProfileEntityByAccount_UserName(String username);

    @NotNull
    @Override
    Optional<ProfileEntity> findById(@NotNull Long id);

    ProfileEntity findByEmailAndAccount_Id(String email, Long id);
}

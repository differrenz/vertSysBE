package com.example.vertsysSecond.service;

import com.example.vertsysSecond.models.Profile;
import com.example.vertsysSecond.persistence.entity.PictureEntity;
import com.example.vertsysSecond.persistence.entity.ProfileEntity;
import com.example.vertsysSecond.persistence.repository.AccountRepository;
import com.example.vertsysSecond.persistence.repository.PictureRepository;
import com.example.vertsysSecond.persistence.repository.ProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ProfileService {
    private static List<String> contentTypes = Arrays.asList("image/png", "image/jpeg", "image/jpg");
    private final ProfileRepository profileRepository;
    private final AccountRepository accountRepository;
    private final PictureRepository pictureRepository;

    public ProfileService(ProfileRepository profileRepository, AccountRepository accountRepository,
                          PictureRepository pictureRepository) {
        this.profileRepository = profileRepository;
        this.accountRepository = accountRepository;
        this.pictureRepository = pictureRepository;
    }

    public void uploadPicture(MultipartFile image, Long id) throws IOException {
        String fileContentType = image.getContentType();
        if (contentTypes.contains(fileContentType)) {
            PictureEntity pictureEntity = new PictureEntity();
            pictureEntity.setProfileEntity(profileRepository.getById(id));
            pictureEntity.setPicByte(image.getBytes());
            pictureEntity.setNamePicture(image.getOriginalFilename());
            pictureEntity.setType(image.getContentType());
            pictureRepository.saveAndFlush(pictureEntity);
        } else {
            throw new RuntimeException("Dateityp der Bilddatei entspricht nicht den Vorgaben(Jpeg oder png).");
        }
    }

    public PictureEntity showPicture(Long id) {
        return pictureRepository.findByProfileEntity_Id(id);
    }

    @Transactional
    public void updatePicture(Long id, MultipartFile image) throws IOException {
        pictureRepository.deletePictureEntityByProfileEntity(profileRepository.getById(id));
        uploadPicture(image, id);
    }

    public ProfileEntity getProfile(Long id) {
        return profileRepository.getById(id);
    }


    public List<Profile> getAllProfiles() {
        List<ProfileEntity> profileEntities = profileRepository.findAll();

        List<Profile> profiles = new ArrayList<>();
        profileEntities.forEach(messageEntity -> {
            Profile tempProfile = new Profile();
            tempProfile.setFirstName(messageEntity.getFirstName());
            tempProfile.setLastName(messageEntity.getLastName());
            tempProfile.setEmail(messageEntity.getEmail());
            tempProfile.setId(messageEntity.getId());
            tempProfile.setAccountId(messageEntity.getAccount().getId());
            profiles.add(tempProfile);
        });


        return profiles;
    }

    public String getName(Long id) {
        ProfileEntity profileEntity = profileRepository.getById(id);
        return profileEntity.getFirstName();
    }

    public Long createProfile(String firstName, String lastName, String email, Long accountId) {
        ProfileEntity profileEntity = new ProfileEntity();
        profileEntity.setFirstName(firstName);
        profileEntity.setLastName(lastName);
        profileEntity.setEmail(email);
        profileEntity.setAccount(accountRepository.getById(accountId));

        return profileRepository.save(profileEntity).getId();
    }

    public void updateEmail(Long id, String email) {
        ProfileEntity updProfileEntity = profileRepository.getById(id);
        if (email != null) {
            updProfileEntity.setEmail(email);
        }
        profileRepository.save(updProfileEntity);
    }

    public void updateProfile(Profile newProfile) {
        ProfileEntity currentProfile = profileRepository.getById(newProfile.getId());
        String validateString = "String";

        if (newProfile.getFirstName() != null && !newProfile.getFirstName().equals(validateString) && !newProfile.getFirstName().equals("")) {
            currentProfile.setFirstName(newProfile.getFirstName());
        }
        if (newProfile.getLastName() != null && !newProfile.getLastName().equals(validateString) && !newProfile.getLastName().equals("")) {
            currentProfile.setLastName(newProfile.getLastName());
        }
        if (newProfile.getEmail() != null && !newProfile.getEmail().equals(validateString) && !newProfile.getEmail().equals("")) {
            currentProfile.setEmail(newProfile.getEmail());
        }
        profileRepository.save(currentProfile);
    }
}

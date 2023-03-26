package com.example.vertsysSecond.controller;


import com.example.vertsysSecond.models.Profile;
import com.example.vertsysSecond.persistence.entity.PictureEntity;
import com.example.vertsysSecond.persistence.entity.ProfileEntity;
import com.example.vertsysSecond.service.ProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@Component
@RestController
@RequestMapping("/profile")
public class ProfileController {
    private final ProfileService profileService;


    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }


    @Operation(summary = "uploadProfilePictureById", description = "uploads a profile picture to the corresponding profile via its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "upload successful", content = @Content),
            @ApiResponse(responseCode = "404", description = "Profile not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "internal error", content = @Content)
    })
    @PostMapping("/uploadImage/{id}")
    public void uploadPicture(MultipartFile image, @PathVariable Long id) throws IOException {
        profileService.uploadPicture(image, id);
    }

    @GetMapping("/showImage/{id}")
    public PictureEntity showPicture(@PathVariable Long id) {
        PictureEntity picture = profileService.showPicture(id);
        return picture;
    }

    @GetMapping("/getAllProfiles")
    public ResponseEntity<List<Profile>> getAllProfiles() {
        return ResponseEntity.of(Optional.of(profileService.getAllProfiles()));
    }

    @PutMapping("/{id}/updateImage")
    public void updatePicture(@PathVariable Long id, MultipartFile image) throws IOException {
        profileService.updatePicture(id, image);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Profile> getProfile(@PathVariable Long id) {
        ProfileEntity profileEntity = profileService.getProfile(id);
        Profile profile = new Profile();
        profile.setFirstName(profileEntity.getFirstName());
        profile.setLastName(profileEntity.getLastName());
        profile.setEmail(profileEntity.getEmail());
        profile.setId(profileEntity.getId());
        profile.setAccountId(profileEntity.getAccount().getId());

        return ResponseEntity.of(Optional.of(profile));
    }

    @GetMapping("/name/{id}")
    public String getProfileName(@PathVariable Long id) {
        return profileService.getName(id);
    }

    @PostMapping("/")
    public ResponseEntity<Long> createProfile(@RequestBody Profile user, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                throw new RuntimeException("Please check your inputs! ");
            }
        } catch (RuntimeException rtec) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        Long profileID = profileService.createProfile(user.getFirstName(), user.getLastName(), user.getEmail(),
                user.getAccountId());
        return ResponseEntity.of(Optional.of(profileID));
    }


    @PutMapping("/{id}")
    public void updateProfile(@RequestBody Profile newProfile) {

        profileService.updateProfile(newProfile);
    }

    @PutMapping("/email/{id}")
    public ResponseEntity<Long> updateEmail(@RequestBody Profile user, @PathVariable Long id) {

        profileService.updateEmail(id, user.getEmail());
        return ResponseEntity.of(Optional.of(id));
    }
}

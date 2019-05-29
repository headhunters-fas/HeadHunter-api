package com.headhunters.controller;

import com.headhunters.model.Profile;
import com.headhunters.service.impl.MapValidationErrorService;
import com.headhunters.service.impl.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @PostMapping("/users/profile")
    public ResponseEntity<?> addProfile(@Valid @RequestBody Profile profile, BindingResult result, Principal principal) {

        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if(errorMap!=null) return errorMap;

        Profile newProfile = profileService.saveProfileWithOwner(profile, principal.getName());

        return new ResponseEntity<Profile>(newProfile, HttpStatus.CREATED);
    }
}

package com.headhunters.service.impl;

import com.headhunters.model.Profile;
import com.headhunters.repository.ProfileRepository;
import com.headhunters.service.Interfaces.IProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileService implements IProfileService {

    @Autowired
    private ProfileRepository profileRepository;


    @Override
    public Profile save(Profile profile) {
        return profileRepository.save(profile);
    }

    @Override
    public Iterable<Profile> findAll() {
        return profileRepository.findAll();
    }

    @Override
    public Profile findById(Long id) {
        return profileRepository.getById(id);
    }

    @Override
    public void delete(Long id) {
        Profile profile = findById(id);
        profileRepository.delete(profile);
    }
}

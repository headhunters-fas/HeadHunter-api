package com.headhunters.repository;

import com.headhunters.model.Profile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends CrudRepository<Profile, Long> {
    Profile getById(Long id);
    Profile findByUsername(String username);
}
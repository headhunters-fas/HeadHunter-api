package com.headhunters.service.impl;

import com.headhunters.exception.UsernameAlreadyExistsException;
import com.headhunters.model.Album;
import com.headhunters.model.Profile;
import com.headhunters.model.User;
import com.headhunters.repository.AlbumRepository;
import com.headhunters.repository.ProfileRepository;
import com.headhunters.repository.UserRepository;
import com.headhunters.service.Interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AlbumRepository albumRepository;
    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public User addAlbum(Long album_id, Long user_id) {
        User user = userRepository.getById(user_id);
        Album album = albumRepository.getById(album_id);
        List<Album> albumList = user.getAlbumList();
        albumList.add(album);
        user.setAlbumList(albumList);
        return userRepository.save(user);
    }

    @Override
    public User addProfile(Long profile_id, Long user_id) {
        User user = userRepository.getById(user_id);
        Profile profile = profileRepository.getById(profile_id);
        user.setProfile(profile);
        return userRepository.save(user);
    }


    @Override
    public User save(User user) {

        try{
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            //Email has to be unique (exception)
            user.setUsername(user.getUsername());
            // Make sure that password and confirmPassword match
            // We don't persist or show the confirmPassword
            user.setConfirmPassword("");
            return userRepository.save(user);

        }catch (Exception e){
            throw new UsernameAlreadyExistsException("Email '"+user.getUsername()+"' already exists");
        }

    }

    @Override
    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        return userRepository.getById(id);
    }

    @Override
    public void delete(Long id) {
        User user = findById(id);
        userRepository.delete(user);
    }
}
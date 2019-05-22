package com.headhunters.service.Interfaces;

import com.headhunters.model.User;
import org.springframework.stereotype.Service;

@Service
public interface IUserService extends IService<User> {

    User addAlbum(Long album_id, Long user_id);
    User addProfile(Long profile_id, Long user_id);
}

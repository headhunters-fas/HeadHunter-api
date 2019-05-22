package com.headhunters.controller;

import com.headhunters.service.Interfaces.IAlbumService;
import com.headhunters.service.Interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.HashMap;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {

    @Autowired
    private IUserService userService;

    @DeleteMapping("/{user_id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long user_id) {
        userService.delete(user_id);

        return new ResponseEntity<String>("User deleted", HttpStatus.OK);
    }

    @RequestMapping("add_album")
    public ResponseEntity<?> addAlbum(@RequestBody HashMap<String, String> mapper) {
        Long album_id = Long.parseLong(mapper.get("album_id"));
        Long user_id = Long.parseLong(mapper.get("user_id"));

        userService.addAlbum(album_id, user_id);

        return new ResponseEntity<String>("Album added to user", HttpStatus.OK);
    }
}

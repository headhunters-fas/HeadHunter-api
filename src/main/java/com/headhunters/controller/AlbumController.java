package com.headhunters.controller;

import com.headhunters.model.Album;
import com.headhunters.service.Interfaces.IAlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/albums")
@CrossOrigin
public class AlbumController {

    @Autowired
    private IAlbumService albumService;

    @PostMapping("")
    public ResponseEntity<?> addAlbum(@Valid @RequestBody Album album, BindingResult result) {

        if(result.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();

            for(FieldError error: result.getFieldErrors()) {
                errorMap.put(error.getField(), error.getDefaultMessage());
            }

            return new ResponseEntity<Map<String, String>>(errorMap, HttpStatus.BAD_REQUEST);
        }

        Album newAlbum = albumService.save(album);

        return new ResponseEntity<Album>(newAlbum, HttpStatus.CREATED);
    }

    @GetMapping("")
    public Iterable<Album> getAllAlbums() {
        return albumService.findAll();
    }

    @GetMapping("/{album_id}")
    public ResponseEntity<?> getAlbumById(@PathVariable Long album_id) {
        Album album = albumService.findById(album_id);
        return new ResponseEntity<Album>(album, HttpStatus.OK);
    }


}

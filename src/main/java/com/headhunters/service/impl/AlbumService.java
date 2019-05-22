package com.headhunters.service.impl;

import com.headhunters.model.Album;
import com.headhunters.model.Song;
import com.headhunters.repository.AlbumRepository;
import com.headhunters.repository.SongRepository;
import com.headhunters.service.Interfaces.IAlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlbumService implements IAlbumService {

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private SongRepository songRepository;

    @Override
    public Album addSong(Long song_id, Long album_id) {
        Album album = albumRepository.getById(album_id);
        Song song = songRepository.getById(song_id);
        List<Song> songList = album.getSongList();
        songList.add(song);
        album.setSongList(songList);
        return albumRepository.save(album);
    }

    @Override
    public Album save(Album album) {
        return albumRepository.save(album);
    }

    @Override
    public Iterable<Album> findAll() {
        return albumRepository.findAll();
    }

    @Override
    public Album findById(Long id) {
        return albumRepository.getById(id);
    }

    @Override
    public void delete(Long id) {
        Album album = findById(id);
        albumRepository.delete(album);
    }
}

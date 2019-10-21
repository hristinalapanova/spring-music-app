package com.ga.service;

import com.ga.dao.SongDao;
import com.ga.entity.Song;
import com.ga.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SongServiceImpl implements SongService {
	
    @Autowired
    SongDao songDao;
    
    @Override
    public List<Song> songList() {
        return songDao.songList();
    }

    @Override
    public Song createSong(Song song) {
        return songDao.createSong(song);
    }

    @Override
    public Song updateSong(Song song, Long songId) {
        return songDao.updateSong(song, songId);
    }

    @Override
    public Song deleteSong(Long songId) {
        return songDao.deleteSong(songId);
    }

    @Override
    public List<User> listeners(Long songId) {
        return songDao.listeners(songId);
    }
}
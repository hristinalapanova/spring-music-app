package com.ga.dao;

import com.ga.entity.Song;
import com.ga.entity.User;

import java.util.List;

public interface SongDao {
    public List<Song> songList();
    public Song createSong(Song song);
    public Song updateSong(Song Song, Long SongId);
    public Song deleteSong(Long SongId);
    public List<User> listeners(Long songId);

}
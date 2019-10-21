package com.ga.controller;

import com.ga.entity.Song;
import com.ga.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/song")
public class SongController {

    @Autowired
    SongService  songService;
    @GetMapping
    public List<Song> songList(){
        return songService.songList();
    }
    @PostMapping
    public Song createSong(@RequestBody Song song){
        return songService.createSong(song);
    }

    @PutMapping("/{songId}")
    public Song updateSong(@RequestBody Song song,
                           @RequestAttribute ("songId") Long songId){
        return songService.updateSong(song, songId);
    }

    @DeleteMapping("/{songId}")
    public Long deleteSong(@RequestAttribute("songId") Long songId){
        return songService.deleteSong(songId).getSongId();
    }

}
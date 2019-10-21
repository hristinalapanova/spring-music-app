package com.ga.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;
@Entity
@Table(name="songs")
public class Song {
	
    @Id
    @Column(name="song_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long songId;
    
    @Column(name="title", nullable = false)
    private String title;
    
    @Column(name="length", nullable = false)
    private int length;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinTable(name="user_song",
    joinColumns = @JoinColumn(name="song_id"),
    inverseJoinColumns = @JoinColumn(name="user_id"))
    private List<User> users;
    
    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Song(Long songId, String title, int length) {
        this.songId = songId;
        this.title = title;
        this.length = length;
    }
    public Song(){};

    public Long getSongId() {
        return songId;
    }

    public void setSongId(Long songId) {
        this.songId = songId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
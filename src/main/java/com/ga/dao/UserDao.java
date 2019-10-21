package com.ga.dao;

import com.ga.entity.Song;
import com.ga.entity.User;

import java.util.List;

public interface UserDao {
    //sign up/ creating an user
    public User createUser(User user);

    public User singIn(User user);

	public User getUserbyId(Long userId);

    public Long deleteUser(Long userId);

    public User updateUser(User user, Long userId);

    public User getUserByUserName(String username);

    public List<Song> getSongs(String username);

    public List<Song> addSong(String username, Long songId);

}
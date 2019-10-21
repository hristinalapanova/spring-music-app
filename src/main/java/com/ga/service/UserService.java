package com.ga.service;

import com.ga.entity.Song;
import com.ga.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
	
    public String signup(User user);

    public String singIn(User user);

    public User getUserbyId(Long userId);
    
    public Long deleteUser(Long userId);

    public User updateUser(User user, Long userId);

    public User getUserByUserName(String username);

    public List<Song> getSongs(String username);

    public List<Song> addSong(String username, Long songId);

}
package com.ga.controller;

import com.ga.entity.JwtResponse;
import com.ga.entity.Song;
import com.ga.entity.User;
import com.ga.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    
    UserService userService;
    
    public UserService getUserService() {
		return userService;
	}

    @Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public UserController() {}

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody User user) {
        return ResponseEntity.ok(new JwtResponse(userService.signup(user)));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        return ResponseEntity.ok(new JwtResponse(userService.singIn(user)));
    }

    @PutMapping("/update/{id}")
    public User update(@PathVariable("id") Long userId, @RequestBody User user) {
        return userService.updateUser(user, userId);
    }

    @DeleteMapping("/delete/{id}")
    public Long delete(@PathVariable("id") Long userId) {
        return userService.deleteUser(userId);
    }

    @PutMapping("/{username}/addsong/{songId}")
    public List<Song> addSong(@PathVariable("username") String username, @PathVariable("songId") Long songId) {
        return userService.addSong(username, songId);
    }

    @GetMapping("/{username}/songs")
    public List<Song>songList(@PathVariable("username") String username){
        return userService.getSongs(username);
    }
    
    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") Long userId) {
    	return userService.getUserbyId(userId);
    }
}


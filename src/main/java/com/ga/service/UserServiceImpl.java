package com.ga.service;

import com.ga.config.JwtUtil;
import com.ga.dao.UserDao;
import com.ga.entity.Song;
import com.ga.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
	
    @Autowired
    @Qualifier("encoder")
    PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    UserDao userDao;
    @Autowired
    JwtUtil jwtUtil;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.getUserByUserName(username);

        if(user==null)
            throw new UsernameNotFoundException("Unkknown user: " +username);

        return new org.springframework.security.core.userdetails.User(user.getUserName(), bCryptPasswordEncoder.encode(user.getPassword()),
                true, true, true, true, getGrantedAuthorities(user));
    }

    private List<GrantedAuthority> getGrantedAuthorities(User user) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

        authorities.add(new SimpleGrantedAuthority("ADMIN"));

        return authorities;
    }


    @Override
    public String signup(User user) {
    	user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        if(userDao.createUser(user) != null) {
            UserDetails userDetails = loadUserByUsername(user.getUserName());

            return jwtUtil.generateToken(userDetails);
        }

        return null;
    }

    @Override
    public String singIn(User user) {
        User foundUser = userDao.singIn(user);
        if(foundUser != null &&
                foundUser.getUserId() != null &&
                bCryptPasswordEncoder.matches(user.getPassword(), foundUser.getPassword())) {
            UserDetails userDetails = loadUserByUsername(foundUser.getUserName());

            return jwtUtil.generateToken(userDetails);
        }

        return null;
    }

    @Override
    public Long deleteUser(Long userId) {
        return userDao.deleteUser(userId);
    }

    @Override
    public User updateUser(User user, Long userId) {
        return userDao.updateUser(user, userId);
    }

    @Override
    public User getUserByUserName(String username) {
        return userDao.getUserByUserName(username);
    }

    @Override
    public List<Song> getSongs(String username) {
        return userDao.getSongs(username);
    }

    @Override
    public List<Song> addSong(String username, Long songId) {
        return userDao.addSong(username, songId);
    }
}
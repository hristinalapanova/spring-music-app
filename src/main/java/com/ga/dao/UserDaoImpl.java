package com.ga.dao;

import com.ga.entity.Song;
import com.ga.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class UserDaoImpl implements UserDao{
    @Autowired
    SessionFactory sessionFactory;

    @Override
    public User createUser(User user) {
        Session session= sessionFactory.getCurrentSession();
        try{
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();


        }
        finally{
            session.close();
        }
        return user;
    }

    @Override
    public User singIn(User user) {
        return getUserByUserName(user.getUserName());
    }

    @Override
    public Long deleteUser(Long userId) {
        Session session= sessionFactory.getCurrentSession();
        try{
            session.beginTransaction();
            User user=session.get(User.class, userId);
            session.delete(user);
            session.getTransaction().commit();

        }
        finally{
            session.close();

        }
        return userId;
    }

    @Override
    public User updateUser(User user, Long userId) {
        Session session= sessionFactory.getCurrentSession();
        User dbuser;
        try{
            session.beginTransaction();
            dbuser=session.get(User.class, userId);
            dbuser.setPassword(user.getPassword());//grabbing the password whn its being changed
            session.update(dbuser);
            session.getTransaction().commit();
        }
        finally{
            session.close();
        }

        return dbuser;
    }

    @Override
    public User getUserByUserName(String username) {
        Session session= sessionFactory.getCurrentSession();
        User dbuser;
        try{
            session.beginTransaction();
            dbuser= (User) session.createQuery("from User u where u.userName='"+
                    username+ "'")
                    .getSingleResult();

        }
        finally{
            session.close();
        }
        return dbuser;
    }

    @Override
    public List<Song> getSongs(String username) {
    	User user = getUserByUserName(username);
    	if (user != null) {
    		return user.getSongs();
    	}

        return null;
    }

    @Override
    public List<Song> addSong(String username, Long songId) {
        User user= getUserByUserName(username);
        Session session= sessionFactory.getCurrentSession();
        try{
            session.beginTransaction();
            Song song= session.get(Song.class, songId);
            user.getSongs().add(song);
            session.update(user);
            session.getTransaction().commit();
        }
        finally{
            session.close();
        }
        return user.getSongs();
    }
}
package com.fix.demo.config.sec;

import com.fix.demo.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a custom implementation of the UserDetailsService class, this class manages the admin username and
 * password using the persistence layer
 */
@Service
public class UserDetailsServiceImp implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /**
     * This method takes the credentials of the administrator from the database and validates them against the
     * login information
     *
     * @param username username in case of multiple administrators
     * @return The administrator with the credentials
     */
    @Override
    public UserDetails loadUserByUsername(String username) {
        com.fix.demo.logic.user.User byUsername = userRepository.findByUsername(username);
        if (byUsername == null) {
            System.out.println("No user found with username: " + username);
            return null; //trow ex here
        }
        List<GrantedAuthority> role_name = new ArrayList<>();
        return new User(byUsername.getUsername(),
                byUsername.getPassword(), true, true,
                true, true, role_name);
    }
}
package com.fix.demo.service;

import com.fix.demo.logic.user.User;
import com.fix.demo.logic.user.UserDTO;
import com.fix.demo.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private UserRepository repository;

    public UserDTO findByUsername(String username) {
        User user = repository.findByUsername(username);
        if (user == null) return null;
        return new UserDTO(user);
    }

    public Iterable<UserDTO> findAll() {
        Iterable<User> all = repository.findAll();
        List<UserDTO> users = new ArrayList<>();
        for (User user : all) {
            users.add(new UserDTO(user));
        }
        if (users.isEmpty()) return null;
        return users;
    }

    public UserDTO save(User user) {
        User save = repository.save(user);
        return new UserDTO(save);
    }

    public UserDTO findById(String id) {
        Optional<User> byId = repository.findById(id);
        return byId.map(UserDTO::new).orElse(null);
    }
}

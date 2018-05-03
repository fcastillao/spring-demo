package com.fix.demo.controller;

import com.fix.demo.logic.user.User;
import com.fix.demo.logic.user.UserDTO;
import com.fix.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * this method queries for a user ID, if no value
     * is specified, then queries for X
     *
     * @param id the ID of the user you want to query
     * @return returns the user without the password
     */
    @RequestMapping(value = "/user", method = GET)
    @ResponseBody
    UserDTO findUser(@RequestParam(value = "id", required = false, defaultValue = "X") String id) {
        return userService.findById(id);
    }

    /**
     * lists all users
     *
     * @return a list of all users without their passwords
     */
    @RequestMapping(value = "/users", method = GET)
    @ResponseBody
    Iterable<UserDTO> users() {
        return userService.findAll();
    }

    /**
     * saves a user in the DB
     *
     * @param username the username, defaults to def
     * @param pass     the password of the user defaults to defps
     * @return the saved user, without password
     */
    @RequestMapping(value = "/user2", method = GET)
    @ResponseBody
    UserDTO saveUser(@RequestParam(value = "user", required = false, defaultValue = "def") String username,
                     @RequestParam(value = "pass", required = false, defaultValue = "defps") String pass) {
        User user = new User(username, pass);
        return userService.save(user);
    }

}
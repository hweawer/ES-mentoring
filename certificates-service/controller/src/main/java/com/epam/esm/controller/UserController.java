package com.epam.esm.controller;

import com.epam.esm.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    private UserService userService;

    @PostMapping(value = "/signup")
    public String register(){
        return "Registration";
    }

    @PostMapping(value = "/login")
    public String login(){
        return "Login";
    }
}

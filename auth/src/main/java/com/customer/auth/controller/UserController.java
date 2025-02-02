package com.customer.auth.controller;

import com.customer.auth.model.User;
import com.customer.auth.service.JWTService;
import com.customer.auth.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;
    private JWTService jwtService;
    public UserController(UserService userService, JWTService jwtService){
        this.jwtService=jwtService;
        this.userService=userService;
    }

    @PostMapping("/register")
    public String registerUser(@RequestBody User user){
       User createdUser = userService.createUser(user);
       return jwtService.generateToken(createdUser.getUsername());

    }
    @PostMapping("/login")
    public String loginUser(@RequestBody User user){
        return userService.verify(user);
    }

    @PostMapping("/logout")
    public String logout(@RequestHeader("Authorization") String authHeader){
        if (authHeader != null && authHeader.startsWith("Bearer ")){
            String token = authHeader.substring(7);
            jwtService.invalidateToken(token);
            return "logout";
        }
        return "Token could not found";
    }
    @GetMapping("/profile")
    public User userProfile(){
        return userService.getUserPrincipal();
    }
    @GetMapping("/list")
    public List<User> getUsers(){
        return userService.getUsers();
    }
    @GetMapping("/{userId}")
    public Optional<User> getUser(@PathVariable int userId){
        return userService.getUser(userId);
    }

    @DeleteMapping("/delete/{userId}")
    public void deleteUser(@PathVariable int userId){
        userService.deleteUser(userId);
    }

}

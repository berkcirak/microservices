package com.customer.auth.service;

import com.customer.auth.model.User;
import com.customer.auth.model.UserDTO;
import com.customer.auth.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private AuthenticationManager authenticationManager;
    private JWTService jwtService;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, AuthenticationManager authenticationManager, JWTService jwtService){
        this.userRepository=userRepository;
        this.bCryptPasswordEncoder=bCryptPasswordEncoder;
        this.authenticationManager=authenticationManager;
        this.jwtService=jwtService;
    }

    public User createUser(User user){
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public Optional<User> getUser(int userId){
        return userRepository.findById(userId);
    }


    public void deleteUser(int userId){
        User authenticatedUser = getAuthenticatedUser();
        if (authenticatedUser.getId() != userId){
            throw new RuntimeException("You are not authorized to update this user");
        }
        userRepository.deleteById(userId);
    }

    public String verify(User user){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        if (authentication.isAuthenticated()){
            return jwtService.generateToken(user.getUsername());
        }
        return "fail";
    }
    public User getAuthenticatedUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails){
            username=((UserDetails) principal).getUsername();
        }else {
            username = principal.toString();
        }
        return userRepository.findByUsername(username);
    }
    public User getUserPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            String username = ((UserDetails) authentication.getPrincipal()).getUsername();
            return userRepository.findByUsername(username); // Veritabanından kullanıcıyı getir
        }

        throw new RuntimeException("Kullanıcı kimliği doğrulanmamış.");
    }

}

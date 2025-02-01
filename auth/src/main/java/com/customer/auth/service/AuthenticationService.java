package com.customer.auth.service;

import com.customer.auth.model.Token;
import com.customer.auth.model.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthenticationService {

    private TokenService tokenService;
    private UserService userService;
    public AuthenticationService(UserService userService, TokenService tokenService){
        this.userService=userService;
        this.tokenService=tokenService;
    }
    public Token registerUser(String username, String password){
        if (userService.getUserByUsername(username) == null){
            User user = new User();
            user.setUsername(username);
            String hashedPassword = hashPassword(password);
            user.setPassword(hashedPassword);
            userService.createUser(user);
            return loginUser(username, password);
        }
        return null;
    }
    public Token loginUser(String username, String password){
        User user = userService.getUserByUsername(username);
        if (user.getUsername() != ""){
            String hashedPassword = hashPassword(password);
            if (hashedPassword.equals(user.getPassword())){
            String token = generateToken();
            String refToken = generateToken();
            tokenService.saveToken(token, username);
            tokenService.saveRefreshToken(refToken, username);
            return new Token(token, refToken, username);
        }
        }
        return null;
    }
    public Token refreshToken(Token oldToken){
        boolean isValid = tokenService.isRefTokenValid(oldToken.getRefToken());
        if (isValid){
            String token = generateToken();
            String newRefToken = generateToken();
            Token newToken = new Token(token, newRefToken, oldToken.getUsername());
            tokenService.invalidateRefreshToken(oldToken.getRefToken());
            tokenService.invalidateToken(oldToken.getToken());
            tokenService.saveToken(newToken.getRefToken(), oldToken.getUsername());
            tokenService.saveRefreshToken(newToken.getRefToken(), oldToken.getUsername());
            return newToken;
        }
        return null;
    }
    public boolean verifyToken(String token){
        return tokenService.getUsernameByToken(token) != null;
    }
    private String generateToken(){
        return UUID.randomUUID().toString();
    }

    private String hashPassword(String password){
        return DigestUtils.sha256Hex(password);
    }


}

package com.chatserver.chatserver.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatserver.chatserver.model.DTOs.LoginDTO;
import com.chatserver.chatserver.model.User;
import com.chatserver.chatserver.repository.UserRepository;
import com.chatserver.chatserver.util.CryptographyUtil;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CryptographyUtil cryptographyUtil;

    public User addUser(User user){
        String prPassword = user.getUserPassword();

        String aftPassword = cryptographyUtil.hashPassword(prPassword);

        user.setUserPassword(aftPassword);

        return userRepository.save(user);
    }

    public User loginUser(LoginDTO loginDTO) {
        try {

            User checkedUser = userRepository.findByUsername(loginDTO.getUserName())
                    .orElseThrow(() -> new RuntimeException("Username not found"));

            boolean passwordCheck = cryptographyUtil.checkPassword(
                    loginDTO.getUserPassword(),
                    checkedUser.getUserPassword()
            );

            if (!passwordCheck) {
                throw new RuntimeException("Password is wrong");
            }

            return checkedUser;

        } catch (Exception e) {
            System.err.println("Login failed: " + e.getMessage());
            e.printStackTrace();
            return null; // or throw a custom exception if you want to send an error to the client
        }
    }
    
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUserByID(Long id){
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("user ID not foud"));
    }
}

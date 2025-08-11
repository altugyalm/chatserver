package com.chatserver.chatserver.util;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class CryptographyUtil {
    

    public String hashPassword(String password){
        String hashedPassword=BCrypt.hashpw(password, BCrypt.gensalt(12));
        return hashedPassword;
    }

    public Boolean checkPassword(String password,String hashedPassword){
        return BCrypt.checkpw(password, hashedPassword);
    }
}

package com.v1.piRo.Cinfrastructure.security;

import com.v1.piRo.Ddomain.IPasswordEncryption;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BcryptPasswordAdapter implements IPasswordEncryption {
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    @Override
    public  String encode(String rawPassword){
        return  encoder.encode(rawPassword);
    }
    @Override
    public boolean matches(String rawPassword,String encodedPassword){
        return  encoder.matches(rawPassword,encodedPassword);
    }
}

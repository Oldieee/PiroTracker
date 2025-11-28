package com.v1.piRo.Ddomain;

import jakarta.servlet.http.PushBuilder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
@Getter
@Setter
public class User {
    private Long id;
    private String username;
    private String password;
    private Set<String> roles=new HashSet<>();

    public  User(){}
    public  User(String username,String password){
        this.username=username;
        this.password=password;
        this.roles.add("ROLE_USER");
    }



}

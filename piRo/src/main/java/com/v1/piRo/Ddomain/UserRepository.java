package com.v1.piRo.Ddomain;

import org.apache.catalina.User;

import java.util.Optional;

public interface UserRepository {
    User save(User user);
    Optional<User>findById(long id);
    Optional<User>findByUsername(String username);
}

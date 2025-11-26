package com.v1.piRo.Ddomain;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

public class User {
    private Long id;
    private String username;
    private String hashedPassword;
    private Set<Long> followingIds=new HashSet<>();

    public void  follow(Long userIdToFollow){
        if(this.id.equals(userIdToFollow)){
            throw new IllegalArgumentException("A user cannot follow themselves.");
        }
        followingIds.add(userIdToFollow);

    }
    public void unFollow(Long userIdToUnfollow){
        followingIds.remove(userIdToUnfollow);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public Set<Long> getFollowingIds() {
        return followingIds;
    }

    public void setFollowingIds(Set<Long> followingIds) {
        this.followingIds = followingIds;
    }
}

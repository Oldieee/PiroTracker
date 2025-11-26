package com.v1.piRo.Cinfrastructure.repository.dbo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name="users")
public class UserDbo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String hashedPassword;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_follows", joinColumns = @JoinColumn(name = "follower_id"))
    @Column(name = "following_id")
    private Set<Long> followingIds;

}
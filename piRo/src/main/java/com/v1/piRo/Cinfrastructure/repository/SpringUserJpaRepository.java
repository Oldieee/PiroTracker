package com.v1.piRo.Cinfrastructure.repository;

import com.v1.piRo.Cinfrastructure.repository.dbo.UserDbo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringUserJpaRepository extends JpaRepository<UserDbo,Long> {
    Optional<UserDbo>findByUsername(String username);
}

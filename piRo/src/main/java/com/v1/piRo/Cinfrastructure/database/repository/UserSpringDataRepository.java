package com.v1.piRo.Cinfrastructure.database.repository;

import com.v1.piRo.Cinfrastructure.database.entity.UserJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserSpringDataRepository extends JpaRepository<UserJpaEntity,Long> {
    Optional<UserJpaEntity>findByUsername(String username);
}

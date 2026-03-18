package com.v1.piRo.Ddomain.repository;

import com.v1.piRo.Cinfrastructure.database.entity.UserJpaEntity; // Stai, aici folosim Entity-ul doar pt rapiditate, dar ideal ar fi un obiect User de domeniu
import java.util.Optional;

public interface UserRepository {
  UserJpaEntity save(UserJpaEntity user);
  Optional<UserJpaEntity> findByUsername(String username);
  Optional<UserJpaEntity> findById(Long id);
}
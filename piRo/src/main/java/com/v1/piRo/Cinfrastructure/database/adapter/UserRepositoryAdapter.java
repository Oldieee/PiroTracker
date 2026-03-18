package com.v1.piRo.Cinfrastructure.database.adapter;

import com.v1.piRo.Cinfrastructure.database.entity.UserJpaEntity;
import com.v1.piRo.Cinfrastructure.database.repository.UserSpringDataRepository;
import com.v1.piRo.Ddomain.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserRepositoryAdapter implements UserRepository {

    private final UserSpringDataRepository springDataRepository;

    public UserRepositoryAdapter(UserSpringDataRepository springDataRepository) {
        this.springDataRepository = springDataRepository;
    }

    @Override
    public UserJpaEntity save(UserJpaEntity user) {
        return springDataRepository.save(user);
    }

    @Override
    public Optional<UserJpaEntity> findByUsername(String username) {
        return springDataRepository.findByUsername(username);
    }

    @Override
    public Optional<UserJpaEntity> findById(Long id) {
        return springDataRepository.findById(id);
    }
}
package com.v1.piRo.Cinfrastructure.database.repository;

import com.v1.piRo.Cinfrastructure.database.entity.WatchlistJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WatchlistSpringDataRepository extends JpaRepository<WatchlistJpaEntity, Long> {
    List<WatchlistJpaEntity> findByUserId(Long userId);
    void deleteByUserIdAndTicker(Long userId, String ticker);
}
package com.v1.piRo.Ddomain.repository; // sau pachetul tău

import com.v1.piRo.Cinfrastructure.database.entity.WatchlistJpaEntity; // <-- IMPORTĂ JPA ENTITY-ul!
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WatchlistRepository extends JpaRepository<WatchlistJpaEntity, Long> { // <-- AICI TREBUIE WatchlistJpaEntity

    List<WatchlistJpaEntity> findByUserId(Long userId);

    Optional<WatchlistJpaEntity> findByUserIdAndTicker(Long userId, String ticker);
}
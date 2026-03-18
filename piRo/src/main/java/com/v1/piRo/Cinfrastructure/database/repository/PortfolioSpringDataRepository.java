package com.v1.piRo.Cinfrastructure.database.repository;

import com.v1.piRo.Cinfrastructure.database.entity.PortfolioJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PortfolioSpringDataRepository extends JpaRepository<PortfolioJpaEntity, Long> {
    List<PortfolioJpaEntity> findByUserId(Long userId);
}
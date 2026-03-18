package com.v1.piRo.Cinfrastructure.database.repository;

import com.v1.piRo.Cinfrastructure.database.entity.TransactionJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionSpringDataRepository extends JpaRepository<TransactionJpaEntity, Long> {

    List<TransactionJpaEntity> findByPortfolioId(Long portfolioId);
}
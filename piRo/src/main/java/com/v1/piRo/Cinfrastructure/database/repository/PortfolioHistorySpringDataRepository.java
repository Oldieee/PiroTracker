package com.v1.piRo.Cinfrastructure.database.repository;

import com.v1.piRo.Cinfrastructure.database.entity.PortfolioHistoryJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
@Repository
public interface PortfolioHistorySpringDataRepository extends JpaRepository<PortfolioHistoryJpaEntity,Long> {
    List<PortfolioHistoryJpaEntity> findAllByOrderByRecordDateAsc();
    Optional<PortfolioHistoryJpaEntity> findByRecordDate(LocalDate date);
}

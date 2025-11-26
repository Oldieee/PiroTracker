package com.v1.piRo.Cinfrastructure.repository;

import com.v1.piRo.Cinfrastructure.repository.dbo.PortfolioDbo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface SpringPortfolioJpaRepository extends JpaRepository<PortfolioDbo,Long> {
    Optional<PortfolioDbo>findByUserId(Long userId);
}

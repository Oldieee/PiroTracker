package com.v1.piRo.Cinfrastructure.repository;

import com.v1.piRo.Cinfrastructure.mappers.PortfolioMapper;
import com.v1.piRo.Cinfrastructure.repository.dbo.PortfolioDbo;
import com.v1.piRo.Ddomain.Portfolio;
import com.v1.piRo.Ddomain.PortfolioRepository;
import com.v1.piRo.Cinfrastructure.repository.dbo.AssetDbo;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository // <-- This annotation is what Spring is looking for
public class PortfolioJpaAdapter implements PortfolioRepository { // <-- Implements the Domain interface

    private final SpringPortfolioJpaRepository jpaRepository;
    private final PortfolioMapper mapper;

    public PortfolioJpaAdapter(SpringPortfolioJpaRepository jpaRepository, PortfolioMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Portfolio save(Portfolio portfolio) {
        // 1. Convert Domain -> DBO
        PortfolioDbo dbo = mapper.toDbo(portfolio);
        // 2. Save DBO
       if(dbo.getAssets()!=null){
           for(AssetDbo asset:dbo.getAssets()){
               asset.setPortfolio(dbo);
           }
       }
        PortfolioDbo savedDbo = jpaRepository.save(dbo);
        // 3. Convert DBO -> Domain
        return mapper.toDomain(savedDbo);
    }

    @Override
    public Optional<Portfolio> findByUserId(Long userId) {
        // 1. Find DBO
        Optional<PortfolioDbo> dboOptional = jpaRepository.findByUserId(userId);
        // 2. Convert DBO -> Domain
        return dboOptional.map(mapper::toDomain);
    }
}
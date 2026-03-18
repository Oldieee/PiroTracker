package com.v1.piRo.Cinfrastructure.database.adapter;

import com.v1.piRo.Cinfrastructure.database.entity.PortfolioJpaEntity;
import com.v1.piRo.Cinfrastructure.database.repository.PortfolioSpringDataRepository;
import com.v1.piRo.Ddomain.Portfolio;
import com.v1.piRo.Ddomain.repository.PortfolioRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class PortfolioRepositoryAdapter implements PortfolioRepository {

    private final PortfolioSpringDataRepository springDataRepository;

    public PortfolioRepositoryAdapter(PortfolioSpringDataRepository springDataRepository) {
        this.springDataRepository = springDataRepository;
    }

    @Override
    public Portfolio save(Portfolio portfolio) {
        PortfolioJpaEntity jpaEntity = new PortfolioJpaEntity();
        jpaEntity.setId(portfolio.getId());
        jpaEntity.setUserId(portfolio.getUserId());
        jpaEntity.setName(portfolio.getName());

        PortfolioJpaEntity savedEntity = springDataRepository.save(jpaEntity);
        return toDomain(savedEntity);
    }

    @Override
    public Optional<Portfolio> findById(Long id) {
        return springDataRepository.findById(id).map(this::toDomain);
    }

    @Override
    public List<Portfolio> findByUserId(Long userId) { // Acum e Long
        return springDataRepository.findByUserId(userId).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    private Portfolio toDomain(PortfolioJpaEntity entity) {
        return new Portfolio(
                entity.getId(),
                entity.getUserId(),
                entity.getName(),
                new ArrayList<>()
        );
    }
}
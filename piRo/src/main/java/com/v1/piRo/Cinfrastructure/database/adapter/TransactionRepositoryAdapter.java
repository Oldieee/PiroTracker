package com.v1.piRo.Cinfrastructure.database.adapter;

import com.v1.piRo.Cinfrastructure.database.entity.AssetJpaEntity;
import com.v1.piRo.Cinfrastructure.database.entity.PortfolioJpaEntity;
import com.v1.piRo.Cinfrastructure.database.entity.TransactionJpaEntity;
import com.v1.piRo.Cinfrastructure.database.repository.TransactionSpringDataRepository;
import com.v1.piRo.Ddomain.Asset;
import com.v1.piRo.Ddomain.Portfolio;
import com.v1.piRo.Ddomain.Transaction;
import com.v1.piRo.Ddomain.repository.TransactionRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class TransactionRepositoryAdapter implements TransactionRepository {

    private final TransactionSpringDataRepository springDataRepository;

    public TransactionRepositoryAdapter(TransactionSpringDataRepository springDataRepository) {
        this.springDataRepository = springDataRepository;
    }

    @Override
    public Transaction save(Transaction transaction) {
        TransactionJpaEntity jpaEntity = new TransactionJpaEntity();
        jpaEntity.setId(transaction.getId());
        jpaEntity.setType(transaction.getType());
        jpaEntity.setPrice(transaction.getPrice());
        jpaEntity.setQuantity(transaction.getQuantity());
        jpaEntity.setTimestamp(transaction.getTimestamp());


        PortfolioJpaEntity portfolioJpa = new PortfolioJpaEntity();
        portfolioJpa.setId(transaction.getPortfolio().getId());
        jpaEntity.setPortfolio(portfolioJpa);


        AssetJpaEntity assetJpa = new AssetJpaEntity();
        assetJpa.setId(transaction.getAsset().getId());
        jpaEntity.setAsset(assetJpa);

        TransactionJpaEntity savedEntity = springDataRepository.save(jpaEntity);
        return toDomain(savedEntity);
    }

    @Override
    public Optional<Transaction> findById(Long id) {
        return springDataRepository.findById(id).map(this::toDomain);
    }
    @Override
    public void deleteById(Long id) {

        springDataRepository.deleteById(id);
    }

    @Override
    public List<Transaction> findByPortfolioId(Long portfolioId) {
        return springDataRepository.findByPortfolioId(portfolioId).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }


    private Transaction toDomain(TransactionJpaEntity entity) {

        Asset assetDomain = new Asset(
                entity.getAsset().getId(),
                entity.getAsset().getTicker(),
                entity.getAsset().getName(),
                entity.getAsset().getExchange(),
                entity.getAsset().getCurrency()
        );


        Portfolio portfolioDomain = new Portfolio(
                entity.getPortfolio().getId(),
                entity.getPortfolio().getUserId(),
                entity.getPortfolio().getName(),
                new ArrayList<>()
        );


        return new Transaction(
                entity.getId(),
                portfolioDomain,
                assetDomain,
                entity.getType(),
                entity.getPrice(),
                entity.getQuantity(),
                entity.getTimestamp()
        );
    }
}
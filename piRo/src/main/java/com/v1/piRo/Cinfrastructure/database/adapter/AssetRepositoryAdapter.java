package com.v1.piRo.Cinfrastructure.database.adapter;

import com.v1.piRo.Cinfrastructure.database.entity.AssetJpaEntity;
import com.v1.piRo.Cinfrastructure.database.repository.AssetSpringDataRepository;
import com.v1.piRo.Ddomain.Asset;
import com.v1.piRo.Ddomain.repository.AssetRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AssetRepositoryAdapter implements AssetRepository {

    private final AssetSpringDataRepository springDataRepository;

    public AssetRepositoryAdapter(AssetSpringDataRepository springDataRepository) {
        this.springDataRepository = springDataRepository;
    }

    @Override
    public Asset save(Asset asset) {
        AssetJpaEntity jpaEntity = new AssetJpaEntity();
        jpaEntity.setId(asset.getId());
        jpaEntity.setTicker(asset.getTicker());
        jpaEntity.setName(asset.getName());
        jpaEntity.setExchange(asset.getExchange());
        jpaEntity.setCurrency(asset.getCurrency());

        AssetJpaEntity savedEntity = springDataRepository.save(jpaEntity);
        return toDomain(savedEntity);
    }

    @Override
    public Optional<Asset> findById(Long id) {
        return springDataRepository.findById(id).map(this::toDomain);
    }

    @Override
    public Optional<Asset> findByTicker(String ticker) {
        return springDataRepository.findByTicker(ticker).map(this::toDomain);
    }

    private Asset toDomain(AssetJpaEntity entity) {
        return new Asset(
                entity.getId(),
                entity.getTicker(),
                entity.getName(),
                entity.getExchange(),
                entity.getCurrency()
        );
    }
}
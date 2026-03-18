package com.v1.piRo.Cinfrastructure.database.repository;

import com.v1.piRo.Cinfrastructure.database.entity.AssetJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AssetSpringDataRepository extends JpaRepository<AssetJpaEntity, Long> {

    Optional<AssetJpaEntity> findByTicker(String ticker);
}
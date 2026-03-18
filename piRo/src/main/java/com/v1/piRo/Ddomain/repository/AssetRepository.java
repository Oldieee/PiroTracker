package com.v1.piRo.Ddomain.repository;

import com.v1.piRo.Ddomain.Asset;
import java.util.Optional;

public interface AssetRepository {
    Asset save(Asset asset);
    Optional<Asset> findById(Long id);
    Optional<Asset> findByTicker(String ticker);
}
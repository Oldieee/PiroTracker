package com.v1.piRo.Ddomain;

import java.util.Optional;

public interface PortfolioRepository {
    Portfolio save(Portfolio portfolio);
    Optional<Portfolio>findByUserId(Long userId);
}

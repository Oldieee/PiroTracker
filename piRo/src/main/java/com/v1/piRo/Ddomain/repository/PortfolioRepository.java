package com.v1.piRo.Ddomain.repository;

import com.v1.piRo.Ddomain.Portfolio;
import java.util.List;
import java.util.Optional;


public interface PortfolioRepository {

    Portfolio save(Portfolio portfolio);

    Optional<Portfolio> findById(Long id);


    List<Portfolio> findByUserId(Long userId);
}
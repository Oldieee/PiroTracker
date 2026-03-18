package com.v1.piRo.Ddomain.repository;

import com.v1.piRo.Ddomain.PortfolioHistory;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PortfolioHistoryRepository {
    void save(PortfolioHistory portfolioHistory);
    List<PortfolioHistory> findAllOrderByDateAsc();
    Optional<PortfolioHistory> findByDate(LocalDate date);
}
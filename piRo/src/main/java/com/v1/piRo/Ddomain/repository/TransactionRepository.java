package com.v1.piRo.Ddomain.repository;

import com.v1.piRo.Ddomain.Transaction;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TransactionRepository {
    Transaction save(Transaction transaction);
    Optional<Transaction> findById(Long id);
    List<Transaction> findByPortfolioId(Long portfolioId);
    void deleteById(Long id);

}
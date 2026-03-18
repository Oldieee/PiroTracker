package com.v1.piRo.Bapplication;


import com.v1.piRo.Ddomain.PortfolioHistory;
import com.v1.piRo.Ddomain.repository.PortfolioHistoryRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PortfolioHistoryQueryService {

    private final PortfolioHistoryRepository repository;

    public PortfolioHistoryQueryService(PortfolioHistoryRepository repository) {
        this.repository = repository;
    }

    public List<PortfolioHistory> getFullHistory() {
        return repository.findAllOrderByDateAsc();
    }
}
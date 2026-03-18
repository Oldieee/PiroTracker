package com.v1.piRo.Bapplication.job;

import com.v1.piRo.Bapplication.PortfolioService;
import com.v1.piRo.Ddomain.PortfolioHistory;
import com.v1.piRo.Ddomain.repository.PortfolioHistoryRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Component
public class DailyPortfolioSnapshotJob {

    private final PortfolioHistoryRepository historyRepository;
    private final PortfolioService portfolioService;

    public DailyPortfolioSnapshotJob(PortfolioHistoryRepository historyRepository, PortfolioService portfolioService) {
        this.historyRepository = historyRepository;
        this.portfolioService = portfolioService;
    }


    @Scheduled(fixedRate = 60000)
    public void saveDailySnapshot() {
        LocalDate today = LocalDate.now();


        Optional<PortfolioHistory> existingSnapshot = historyRepository.findByDate(today);
        if (existingSnapshot.isPresent()) {
            System.out.println("Snapshot-ul pentru azi a fost deja salvat.");
            return;
        }




        Double currentTotalWorth = portfolioService.getPortfolioSummary(1L).totalNetWorth().doubleValue();


        PortfolioHistory snapshot = new PortfolioHistory(null, today, currentTotalWorth);
        historyRepository.save(snapshot);

        System.out.println("✅ Snapshot salvat cu succes pentru " + today + ": " + currentTotalWorth + " €");
    }
}
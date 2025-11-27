package com.v1.piRo.Bapplication;

import com.v1.piRo.Ddomain.MarketDataRepository;
import com.v1.piRo.Ddomain.PortfolioRepository;
import com.v1.piRo.Ddomain.VO.Asset;
import com.v1.piRo.Ddomain.VO.Money;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class MarketUpdateService {
    private final PortfolioRepository portfolioRepository;
    private final MarketDataRepository marketDataRepository;
    public MarketUpdateService(PortfolioRepository portfolioRepository,MarketDataRepository marketDataRepository) {
        this.portfolioRepository = portfolioRepository;
        this.marketDataRepository = marketDataRepository;
    }

@Scheduled(fixedRate = 60000)
@Transactional
public void updateAllAssetPrices() {
    System.out.println("Incep actualizarea preturilor..");
    portfolioRepository.findByUserId(1L).ifPresent(portfolio -> {
        for (Asset asset : portfolio.getAssets()) {
            String ticker = asset.getTickerSymbol();
            BigDecimal livePrice = marketDataRepository.getPrice(ticker);
            if (livePrice.compareTo(BigDecimal.ZERO) > 0) {
                Money newPrice = new Money(livePrice, asset.getPurchasePrice().getCurrency());
                asset.setCurrentPrice(newPrice);
                System.out.println("   -> Actualizat " + ticker + ": " + livePrice);
            } else {
                System.out.println("   -> Eșec la " + ticker + " (Yahoo a dat 0)");

            }
        }
        portfolioRepository.save(portfolio);

    });
    System.out.println("Actualizare finalizata");
}
}

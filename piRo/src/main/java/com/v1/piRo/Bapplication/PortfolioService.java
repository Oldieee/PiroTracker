package com.v1.piRo.Bapplication;

import com.v1.piRo.Aexposition.HoldingResponse;
import com.v1.piRo.Aexposition.PortfolioSummaryResponse;
import com.v1.piRo.Aexposition.StockQuote;
import com.v1.piRo.Bapplication.TransactionResponse;
import com.v1.piRo.Cinfrastructure.database.entity.UserJpaEntity;
import com.v1.piRo.Cinfrastructure.database.entity.WatchlistJpaEntity;
import com.v1.piRo.Ddomain.*;
import com.v1.piRo.Ddomain.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final AssetRepository assetRepository;
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    private  final WatchlistRepository watchlistRepository;
    private final MarketDataService marketDataService;
    public PortfolioService(PortfolioRepository portfolioRepository,
                            AssetRepository assetRepository,
                            TransactionRepository transactionRepository,
                            UserRepository userRepository,WatchlistRepository watchlistRepository,
                            MarketDataService marketDataService) {
        this.portfolioRepository = portfolioRepository;
        this.assetRepository = assetRepository;
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
        this.watchlistRepository=watchlistRepository;
        this.marketDataService=marketDataService;
    }


    @Transactional
    public void addTransactionToPortfolio(Long userId, String ticker, BigDecimal quantity, BigDecimal priceAmount, String currency) {


        Portfolio portfolio = portfolioRepository.findByUserId(userId).stream()
                .findFirst()
                .orElseGet(() -> {
                    Portfolio newPortfolio = new Portfolio(userId, "Portofoliul Meu");
                    return portfolioRepository.save(newPortfolio);
                });


        Asset asset = assetRepository.findByTicker(ticker)
                .orElseGet(() -> {
                    Asset newAsset = new Asset(ticker, ticker, "BVB", currency);
                    return assetRepository.save(newAsset);
                });


        Transaction transaction = new Transaction(
                portfolio,
                asset,
                TransactionType.BUY,
                priceAmount,
                quantity,
                LocalDateTime.now()
        );


        transactionRepository.save(transaction);
    }



    public Portfolio getPortfolio(Long userId) {
        return portfolioRepository.findByUserId(userId).stream()
                .findFirst()
                .orElseGet(() -> {
                    System.out.println("⚠️ Portofoliu negăsit. Inițializăm contul pentru user: " + userId);


                    UserJpaEntity user = userRepository.findById(userId)
                            .orElseGet(() -> {
                                UserJpaEntity newUser = new UserJpaEntity();
                                newUser.setUsername("Utilizator_Demo");
                                newUser.setEmail("demo@piro.com");
                                return userRepository.save(newUser);
                            });


                    Portfolio newPortfolio = new Portfolio(user.getId(), "Portofoliul Meu Principal");
                    return portfolioRepository.save(newPortfolio);
                });
    }

    @Transactional
    public void removeTransaction(Long userId, Long transactionId) {
        if (transactionId == null) {
            System.err.println(" Eroare: transactionId este NULL!");
            return;
        }

        transactionRepository.deleteById(transactionId);
        System.out.println("🗑️ Tranzacția " + transactionId + " a fost ștearsă.");
    }

    public BigDecimal calculateTotalValue(Long userId) {

        return BigDecimal.ZERO;
    }

    public List<Transaction> getTransactionsByPortfolioId(Long portfolioId) {
        return transactionRepository.findByPortfolioId(portfolioId);
    }
    @Transactional(readOnly = true)
    public List<StockQuote> getFullWatchlist(Long userId) {

        List<WatchlistJpaEntity> userWatchlist = watchlistRepository.findByUserId(userId);


        return userWatchlist.stream()
                .map(item -> marketDataService.getQuote(item.getTicker()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void addToWatchlist(Long userId, String ticker) {
        String upperTicker = ticker.toUpperCase();

        Optional<WatchlistJpaEntity> existing = watchlistRepository.findByUserIdAndTicker(userId, upperTicker);
        if (existing.isEmpty()) {

            WatchlistJpaEntity newItem = new WatchlistJpaEntity(userId, upperTicker);
            watchlistRepository.save(newItem);
            System.out.println("✅ " + upperTicker + " adăugat în watchlist.");
        }
    }

    @Transactional
    public void removeFromWatchlist(Long userId, String ticker) {
        String upperTicker = ticker.toUpperCase();

        watchlistRepository.findByUserIdAndTicker(userId, upperTicker)
                .ifPresent(item -> {
                    watchlistRepository.delete(item);
                    System.out.println("🗑️ " + upperTicker + " șters din watchlist-ul userului " + userId);
                });
    }
    @Transactional(readOnly = true)
    public PortfolioSummaryResponse getPortfolioSummary(Long userId){
        Portfolio portfolio=getPortfolio(userId);
        List<Transaction>transactions=transactionRepository.findByPortfolioId(portfolio.getId());
        Map<String,List<Transaction>>groupedTxs=transactions.stream()
                .collect(Collectors.groupingBy(tx->tx.getAsset().getTicker()));
        List<HoldingResponse>holdings=new ArrayList<>();
        BigDecimal totalNetWorth=BigDecimal.ZERO;
        BigDecimal totalInvested=BigDecimal.ZERO;
        for (Map.Entry<String,List<Transaction>>entry: groupedTxs.entrySet()){
            String ticker=entry.getKey();
            List<Transaction>txs=entry.getValue();
            BigDecimal qty=BigDecimal.ZERO;
            BigDecimal cost=BigDecimal.ZERO;
            for (Transaction tx:txs){
                if(tx.getType()==TransactionType.BUY){
                    qty=qty.add(tx.getQuantity());
                    cost=cost.add(tx.getPrice().multiply(tx.getQuantity()));

                }else {
                    qty=qty.subtract(tx.getQuantity());
                }
            }
            if(qty.compareTo(BigDecimal.ZERO)<=0)continue;

            BigDecimal avgPrice=cost.divide(qty,2, RoundingMode.HALF_UP);
            StockQuote liveData=marketDataService.getQuote(ticker);

            BigDecimal currentValueNative = qty.multiply(liveData.currentPrice());
            BigDecimal profitNative = currentValueNative.subtract(cost);
            BigDecimal profitPctNative = cost.compareTo(BigDecimal.ZERO) > 0
                    ? profitNative.divide(cost, 4, RoundingMode.HALF_UP).multiply(new BigDecimal("100"))
                    : BigDecimal.ZERO;

            BigDecimal valueInEur = currentValueNative;
            BigDecimal costInEur = cost;

            if ("RON".equals(liveData.currency())) {
                BigDecimal cursEurRon = marketDataService.getLiveEurRonRate();
                valueInEur = currentValueNative.divide(cursEurRon, 2, RoundingMode.HALF_UP);
                costInEur = cost.divide(cursEurRon, 2, RoundingMode.HALF_UP);
            } else if ("USD".equals(liveData.currency())) {
                BigDecimal cursEurUsd = marketDataService.getLiveEurUsdRate();
                valueInEur = currentValueNative.divide(cursEurUsd, 2, RoundingMode.HALF_UP);
                costInEur = cost.divide(cursEurUsd, 2, RoundingMode.HALF_UP);
            }
            totalNetWorth=totalNetWorth.add(valueInEur);
            totalInvested=totalInvested.add(costInEur);
            holdings.add(new HoldingResponse(
                    ticker,
                    qty,
                    avgPrice,
                    liveData.currentPrice(),
                    liveData.currency(),
                    currentValueNative,
                    valueInEur,
                    profitNative,
                    profitPctNative
            ));
        }
        BigDecimal totalProfit = totalNetWorth.subtract(totalInvested);
        BigDecimal totalProfitPct = totalInvested.compareTo(BigDecimal.ZERO) > 0
                ? totalProfit.divide(totalInvested, 4, RoundingMode.HALF_UP).multiply(new BigDecimal("100"))
                : BigDecimal.ZERO;

       List<TransactionResponse> txHistory=transactions.stream()
               .map(tx->new TransactionResponse(
                       tx.getId(),
                       tx.getAsset().getTicker(),
                       tx.getType().name(),
                       tx.getPrice(),
                       tx.getQuantity(),
                       tx.getTimestamp()
               )).sorted((t1,t2)->t2.timestamp().compareTo(t1.timestamp()))
               .collect(Collectors.toList());
        return new PortfolioSummaryResponse(
                portfolio.getName(), totalNetWorth, totalInvested, totalProfit, totalProfitPct, holdings,txHistory
        );
    }
}

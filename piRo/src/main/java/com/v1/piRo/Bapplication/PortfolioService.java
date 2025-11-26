package com.v1.piRo.Bapplication;

import com.v1.piRo.Ddomain.MarketDataRepository;
import com.v1.piRo.Ddomain.Portfolio;
import com.v1.piRo.Ddomain.PortfolioRepository;
import com.v1.piRo.Ddomain.VO.Asset;
import com.v1.piRo.Ddomain.VO.Money;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

import static org.apache.logging.log4j.ThreadContext.isEmpty;

@Service
public class PortfolioService {
 private  final PortfolioRepository portfolioRepository;
 private final MarketDataRepository marketDataRepository;
 public PortfolioService(PortfolioRepository portfolioRepository,MarketDataRepository marketDataRepository){
     this.portfolioRepository=portfolioRepository;
     this.marketDataRepository=marketDataRepository;
 }

 @Transactional
    public  void addAssetToPortfolio(Long userId, String rawTicker, BigDecimal quantity,BigDecimal manualPrice,String currency){
     if(rawTicker==null||rawTicker.trim().isEmpty()){
         throw new IllegalArgumentException("Simbolul nu poate fi gasit.");
     }
     String ticker=rawTicker.trim().toUpperCase();

     Portfolio portfolio=portfolioRepository.findByUserId(userId).orElseGet(()->createEmptyPortfolio(userId));

        BigDecimal currentMarketPrice=marketDataRepository.getPrice(ticker);
        BigDecimal finalPrice=(currentMarketPrice.compareTo(BigDecimal.ZERO) >0)
                                                ?currentMarketPrice
                                                :manualPrice;
     Optional<Asset>existingAssetOpt=portfolio.getAssets().stream()
             .filter(asset -> asset.getTickerSymbol().equals(ticker))
             .findFirst();
     if(existingAssetOpt.isPresent()){
         Asset existingAsset= existingAssetOpt.get();
         BigDecimal oldQuantity=existingAsset.getQuantity();
         BigDecimal newTotalQuantity=oldQuantity.add(quantity);

         BigDecimal oldValue=oldQuantity.multiply(existingAsset.getPurchasePrice().getAmount());
         BigDecimal newValue=quantity.multiply(finalPrice);

         BigDecimal totalValue = oldValue.add(newValue);
         BigDecimal newAvaragePrice=totalValue.divide(newTotalQuantity,2, RoundingMode.HALF_UP);

         existingAsset.setQuantity(newTotalQuantity);
         existingAsset.setPurchasePrice(new Money(newAvaragePrice,currency));
         System.out.println("Activ existent actualizat: "+ticker+"Cantitate noua:"+newTotalQuantity);

     }else {
         Money purchasePrice = new Money(finalPrice, currency);
         Asset newAsset = new Asset();
         newAsset.setTickerSymbol(ticker.toUpperCase());
         newAsset.setQuantity(quantity);
         newAsset.setPurchasePrice(purchasePrice);
         portfolio.addAsset(newAsset);
         System.out.println("Activ nou creat: "+ticker);
     }
     portfolioRepository.save(portfolio);
 }
 @Transactional
 public void removeAssetFromPortfolio(Long userId,Long assetId){
     Portfolio portfolio=portfolioRepository.findByUserId(userId).orElseThrow(()->new RuntimeException("Portofoliul nu exista"));
        portfolio.getAssets().removeIf(asset->asset.getId().equals(assetId));
        portfolioRepository.save(portfolio);
     }
 private  Portfolio createEmptyPortfolio(Long userId){
     Portfolio p=new Portfolio();
     p.setUserId(userId);
     return p;
 }
 @Transactional(readOnly=true)
    public  Portfolio getPortfolio(Long userId){
     return portfolioRepository.findByUserId(userId)
             .orElseThrow(()->new RuntimeException("Portfolio not found for user"));
 }
}

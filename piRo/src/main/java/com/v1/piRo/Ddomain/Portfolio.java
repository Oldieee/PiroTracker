package com.v1.piRo.Ddomain;

import com.v1.piRo.Ddomain.VO.Money;
import com.v1.piRo.Ddomain.VO.Asset;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Portfolio {
    private Long id;
    private Long userId;
    private Set<Asset> assets = new HashSet<>();

    public void addAsset(Asset asset) {
        this.assets.add(asset);
    }

    public void removeAsset(Asset asset) {
        this.assets.remove(asset);
    }

    public Money calculateTotalValue(Map<String, Money> currentPrices) {
        Money totalValue = new Money(BigDecimal.ZERO, "USD");
        for (Asset asset : assets) {
            Money currentPrice = currentPrices.get(asset.getTickerSymbol());
            if (currentPrice != null) {
                totalValue = totalValue.add(currentPrice.multiply(asset.getQuantity()));
            }

        }
        return totalValue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Set<Asset> getAssets() {
        return assets;
    }

    public void setAssets(Set<Asset> assets) {
        this.assets = assets;
    }


}
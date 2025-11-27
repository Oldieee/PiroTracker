package com.v1.piRo.Ddomain;

import com.v1.piRo.Ddomain.VO.Money;
import com.v1.piRo.Ddomain.VO.Asset;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
@Getter
@Setter
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

    public BigDecimal getTotalValue(){
        BigDecimal total=BigDecimal.ZERO;
        if(assets!=null){
            for(Asset asset:assets){
                Money priceToUse=(asset.getCurrentPrice()!=null)
                                ?asset.getCurrentPrice()
                                :asset.getPurchasePrice();
                if(priceToUse!=null){
                    BigDecimal assetValue=asset.getQuantity().multiply(priceToUse.getAmount());
                    total=total.add(assetValue);
                }
            }
        }
        return  total;
    }



}
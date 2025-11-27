package com.v1.piRo.Ddomain.VO;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
public class Asset {
    private Long id;
    private String tickerSymbol;
    private BigDecimal quantity;
    private Money purchasePrice;
    private Money currentPrice;



}

package com.v1.piRo.Ddomain;

import java.math.BigDecimal;

public interface MarketDataRepository {
    BigDecimal getPrice(String ticker);
}

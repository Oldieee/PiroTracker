package com.v1.piRo.Aexposition;

import java.math.BigDecimal;

public record HoldingResponse(
        String ticker,
        BigDecimal totalQuantity,
        BigDecimal averagePrice,
        BigDecimal currentPrice,
        String currency,
        BigDecimal totalValue,
        BigDecimal valueInEur,
        BigDecimal profitLossAmount,
        BigDecimal profitLossPercentage
) {}
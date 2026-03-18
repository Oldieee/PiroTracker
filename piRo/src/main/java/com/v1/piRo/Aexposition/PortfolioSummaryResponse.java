package com.v1.piRo.Aexposition;

import com.v1.piRo.Bapplication.TransactionResponse;

import java.math.BigDecimal;
import java.util.List;

public record PortfolioSummaryResponse(
        String portfolioName,
        BigDecimal totalNetWorth,
        BigDecimal totalInvested,
        BigDecimal totalProfitLoss,
        BigDecimal totalProfitPercentage,
        List<HoldingResponse> holdings,
        List<TransactionResponse>transactions
) {}
package com.v1.piRo.Bapplication;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionResponse(
        Long id,
        String ticker,
        String type,
        BigDecimal price,
        BigDecimal quantity,
        LocalDateTime timestamp
) {
}

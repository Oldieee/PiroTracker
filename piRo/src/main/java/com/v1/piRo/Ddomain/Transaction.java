package com.v1.piRo.Ddomain;

import lombok.Getter;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Getter
public class Transaction {

    private final Long id;
    private final Portfolio portfolio;
    private final Asset asset;
    private final TransactionType type;
    private final BigDecimal price;
    private final BigDecimal quantity;
    private final LocalDateTime timestamp;


    public Transaction(Portfolio portfolio, Asset asset, TransactionType type,
                       BigDecimal price, BigDecimal quantity, LocalDateTime timestamp) {
        this.id = null;
        this.portfolio = portfolio;
        this.asset = asset;
        this.type = type;
        this.price = price;
        this.quantity = quantity;
        this.timestamp = timestamp;
    }


    public Transaction(Long id, Portfolio portfolio, Asset asset, TransactionType type,
                       BigDecimal price, BigDecimal quantity, LocalDateTime timestamp) {
        this.id = id;
        this.portfolio = portfolio;
        this.asset = asset;
        this.type = type;
        this.price = price;
        this.quantity = quantity;
        this.timestamp = timestamp;
    }
}
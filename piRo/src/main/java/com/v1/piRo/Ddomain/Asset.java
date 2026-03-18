package com.v1.piRo.Ddomain;

import lombok.Getter;

@Getter
public class Asset {

    private final Long id;
    private final String ticker;
    private final String name;
    private final String exchange;
    private final String currency;


    public Asset(String ticker, String name, String exchange, String currency) {
        this.id = null;
        this.ticker = ticker;
        this.name = name;
        this.exchange = exchange;
        this.currency = currency;
    }


    public Asset(Long id, String ticker, String name, String exchange, String currency) {
        this.id = id;
        this.ticker = ticker;
        this.name = name;
        this.exchange = exchange;
        this.currency = currency;
    }
}
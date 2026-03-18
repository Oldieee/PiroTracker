package com.v1.piRo.Ddomain;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Watchlist {

    private Long id;
    private Long userId;
    private String ticker;

    public Watchlist(Long id, Long userId, String ticker) {
        this.id = id;
        this.userId = userId;
        this.ticker = ticker;
    }
}
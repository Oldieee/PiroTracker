package com.v1.piRo.Cinfrastructure.database.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "watchlist")
public class WatchlistJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "ticker", nullable = false)
    private String ticker;

    public WatchlistJpaEntity() {}

    public WatchlistJpaEntity(Long userId, String ticker) {
        this.userId = userId;
        this.ticker = ticker;
    }


}
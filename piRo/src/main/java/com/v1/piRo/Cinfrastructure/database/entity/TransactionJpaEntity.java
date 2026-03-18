package com.v1.piRo.Cinfrastructure.database.entity;

import com.v1.piRo.Ddomain.TransactionType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Getter
@Setter
public class TransactionJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id", nullable = false)
    private PortfolioJpaEntity portfolio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asset_id", nullable = false)
    private AssetJpaEntity asset;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;

    @Column(nullable = false, precision = 10, scale = 4)
    private BigDecimal price;

    @Column(nullable = false, precision = 10, scale = 4)
    private BigDecimal quantity;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    public TransactionJpaEntity() {}
}
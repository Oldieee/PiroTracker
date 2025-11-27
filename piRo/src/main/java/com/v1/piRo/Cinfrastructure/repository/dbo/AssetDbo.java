package com.v1.piRo.Cinfrastructure.repository.dbo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
@Entity
@Table(name="assets")
public class AssetDbo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
    @Column(nullable = false)
    private String tickerSymbol;

    @Column(nullable = false)
    private BigDecimal quantity;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="amount",column = @Column(name = "purchase_price_amount")),
            @AttributeOverride(name="currency",column =@Column(name = "purchase_price_currency"))
     })

    private  MoneyDbo purchasePrice;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="amount",column = @Column(name = "current_price_amount")),
            @AttributeOverride(name="currency",column =@Column(name = "current_price_currency"))
    })
    private MoneyDbo currentPrice;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id",nullable = false)
    private PortfolioDbo portfolio;


}

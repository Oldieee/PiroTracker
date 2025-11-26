package com.v1.piRo.Cinfrastructure.repository.dbo;

import jakarta.persistence.*;

import java.math.BigDecimal;

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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id",nullable = false)
    private PortfolioDbo portfolio;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTickerSymbol() {
        return tickerSymbol;
    }

    public void setTickerSymbol(String tickerSymbol) {
        this.tickerSymbol = tickerSymbol;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public MoneyDbo getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(MoneyDbo purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public PortfolioDbo getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(PortfolioDbo portfolio) {
        this.portfolio = portfolio;
    }
}

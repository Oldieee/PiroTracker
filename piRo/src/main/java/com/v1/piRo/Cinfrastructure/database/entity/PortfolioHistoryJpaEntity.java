package com.v1.piRo.Cinfrastructure.database.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@Entity
@Table(name = "portfolio_history")
public class PortfolioHistoryJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "record_date", nullable = false)
    private LocalDate recordDate;

    @Column(name = "total_net_worth", nullable = false)
    private Double totalNetWorth;


    public PortfolioHistoryJpaEntity() {}
}

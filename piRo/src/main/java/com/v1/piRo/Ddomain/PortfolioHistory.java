package com.v1.piRo.Ddomain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@AllArgsConstructor
@Getter
@Setter
public class PortfolioHistory {
    private Long id;
    private LocalDate recordDate;
    private Double totalNetWorth;
}

package com.v1.piRo.Cinfrastructure.repository.dbo;


import com.v1.piRo.Ddomain.VO.Money;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
@Embeddable
public class MoneyDbo {
@Column(name = "price_amount")
    private BigDecimal amount;
@Column(name="price_currency")
    private String currency;
public MoneyDbo(){}
    public MoneyDbo(BigDecimal amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }


}

package com.v1.piRo.Ddomain.VO;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.Objects;
@Getter
public  final class Money {
    private final BigDecimal amount;
    private final String currency;
    public Money(BigDecimal amount,String currency){
        if(amount==null||amount.compareTo(BigDecimal.ZERO)<0){
            throw new IllegalArgumentException("Amount cannot be null or negative");
        }
        this.amount=amount;
        this.currency=currency;
    }
    public Money add(Money other){
        if (!this.currency.equals(other.currency)){
            throw new IllegalArgumentException("Cannot add different currencies");

        }
        return new Money(this.amount.add(other.amount),this.currency);
    }
    public Money multiply(BigDecimal factor){
        return new Money(this.amount.multiply(factor),this.currency);
    }
    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return amount.equals(money.amount) && currency.equals(money.currency);
    }

    @Override
    public int hashCode(){
        return Objects.hash(amount,currency);
    }


}

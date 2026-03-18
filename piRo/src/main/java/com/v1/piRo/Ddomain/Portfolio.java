package com.v1.piRo.Ddomain;

import lombok.Getter;
import java.util.ArrayList;
import java.util.List;

@Getter
public class Portfolio {

  private final Long id;
  private final Long userId;
  private String name;
  private List<Transaction> transactions = new ArrayList<>();


  public Portfolio(Long userId, String name) {
    this.id = null;
    this.userId = userId;
    this.name = name;
  }


  public Portfolio(Long id, Long userId, String name, List<Transaction> transactions) {
    this.id = id;
    this.userId = userId;
    this.name = name;
    if (transactions != null) {
      this.transactions = transactions;
    }
  }

  public void addTransaction(Transaction transaction) {
    this.transactions.add(transaction);
  }
}
package com.kion.bunga.domain;

import java.util.Collections;
import java.util.List;

public class Report {

  public static final Report EMPTY_STATE = new Report();
  static {
    EMPTY_STATE.iban = "";
    EMPTY_STATE.transactionsTotal = 0;
    EMPTY_STATE.transactions = Collections.EMPTY_LIST;
  }

  String iban;
  Integer transactionsTotal;
  List<TransactionInfo> transactions;

  public String getIban() {
    return iban;
  }

  public void setIban(String iban) {
    this.iban = iban;
  }

  public Integer getTransactionsTotal() {
    return transactionsTotal;
  }

  public void setTransactionsTotal(Integer transactionsTotal) {
    this.transactionsTotal = transactionsTotal;
  }

  public List<TransactionInfo> getTransactions() {
    return transactions;
  }

  public void setTransactions(List<TransactionInfo> transactions) {
    this.transactions = transactions;
  }
}

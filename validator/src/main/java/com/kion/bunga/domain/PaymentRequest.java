package com.kion.bunga.domain;

import javax.validation.constraints.NotNull;

public class PaymentRequest {

  @NotNull
  private String sender;

  @NotNull
  private String receiver;

  @NotNull
  private Integer amount;

  @NotNull
  private TransactionType transactionType;

  public String getSender() {
    return sender;
  }

  public void setSender(String sender) {
    this.sender = sender;
  }

  public String getReceiver() {
    return receiver;
  }

  public void setReceiver(String receiver) {
    this.receiver = receiver;
  }

  public Integer getAmount() {
    return amount;
  }

  public void setAmount(Integer amount) {
    this.amount = amount;
  }

  public TransactionType getTransactionType() {
    return transactionType;
  }

  public void setTransactionType(TransactionType transactionType) {
    this.transactionType = transactionType;
  }
}

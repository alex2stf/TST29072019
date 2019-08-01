package com.kion.bunga.domain;

import javax.validation.constraints.NotNull;

public class PaymentDTO  {

  @NotNull
  private String uid;

  @NotNull
  private String sender;

  @NotNull
  private String receiver;

  @NotNull
  private Integer amount;

  @NotNull
  private String name;

  private String description;

  private String transactionType;

  public String getUid() {
    return uid;
  }

  public void setUid(String uid) {
    this.uid = uid;
  }

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

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getTransactionType() {
    return transactionType;
  }

  public void setTransactionType(String transactionType) {
    this.transactionType = transactionType;
  }

  @Override
  public String toString() {
    return "PaymentDTO{" +
        "uid='" + uid + '\'' +
        ", sender='" + sender + '\'' +
        ", receiver='" + receiver + '\'' +
        ", amount=" + amount +
        ", name='" + name + '\'' +
        ", description='" + description + '\'' +
        ", transactionType='" + transactionType + '\'' +
        '}';
  }
}

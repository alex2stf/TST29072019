package com.kion.bunga.entities;

import com.kion.bunga.domain.TransactionType;

public class Transaction {

  private Account sender;

  private Account receiver;

  private Long id;

  private String uid;

  private Integer amount;

  private TransactionType transactionType;
}

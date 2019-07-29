package com.kion.bunga.errors;

import org.springframework.http.HttpStatus;

public class InsufficientFundsException  extends RestException  {

  public InsufficientFundsException() {
    super(HttpStatus.PAYMENT_REQUIRED, "Insufficient funds");
  }
}

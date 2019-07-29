package com.kion.bunga.errors;

import org.springframework.http.HttpStatus;

public class AccountNotFoundException extends RestException {

  public AccountNotFoundException() {
    super(HttpStatus.PRECONDITION_FAILED, "Account not found");
  }
}

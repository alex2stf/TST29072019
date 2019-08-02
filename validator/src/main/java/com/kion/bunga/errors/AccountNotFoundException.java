package com.kion.bunga.errors;

import org.springframework.http.HttpStatus;

public class AccountNotFoundException extends RestException {

  public AccountNotFoundException() {
    super(HttpStatus.NOT_FOUND, "Invalid accounts");
  }
}

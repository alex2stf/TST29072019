package com.kion.bunga.errors;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends RestException {

  public ResourceNotFoundException(String message) {
    super(HttpStatus.BAD_REQUEST, message);
  }
}

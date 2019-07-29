package com.kion.bunga.errors;

import org.springframework.http.HttpStatus;

public abstract class RestException extends Exception {

  private final HttpStatus status;

  public RestException(HttpStatus status, String message){
    super(message);
    this.status = status;
  }

  public RestException(HttpStatus status, String message, Throwable cause){
    super(message, cause);
    this.status = status;
  }

  public HttpStatus getStatus() {
    return status;
  }
}

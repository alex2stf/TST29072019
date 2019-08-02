package com.kion.bunga.web.rest;


import com.kion.bunga.config.Authorized;
import com.kion.bunga.domain.Proto;
import com.kion.bunga.services.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {

  private final TransactionService transactionService;

  public TransactionController(TransactionService transactionService) {
    this.transactionService = transactionService;
  }

  @Authorized
  @PostMapping(value = "/transaction", consumes = "application/x-protobuf", produces = "application/x-protobuf")
  public ResponseEntity<Proto.TransactionResponse> createTransaction(@RequestBody Proto.Payment payment){
    return new ResponseEntity<>(transactionService.save(payment), HttpStatus.OK);
  }



}

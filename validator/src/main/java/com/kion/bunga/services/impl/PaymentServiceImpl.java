package com.kion.bunga.services.impl;

import com.kion.bunga.domain.Convertors;
import com.kion.bunga.domain.PaymentDTO;
import com.kion.bunga.domain.PaymentRequest;
import com.kion.bunga.domain.Proto.TransactionResponse;
import com.kion.bunga.errors.AccountNotFoundException;
import com.kion.bunga.services.PaymentService;
import com.kion.bunga.services.PersistenceService;
import java.util.concurrent.CompletableFuture;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {




  private final PersistenceService persistenceService;

  public PaymentServiceImpl(PersistenceService persistenceService) {
    this.persistenceService = persistenceService;
  }


  public CompletableFuture<String> create(PaymentRequest request) {
    return CompletableFuture.supplyAsync(() -> {
      PaymentDTO paymentDTO = Convertors.fromRequest(request);
      TransactionResponse response = persistenceService.persist(paymentDTO);
      if (response != null && "PARTICIPANTS_NOT_FOUND".equals(response.getMessage())){
        throw new AccountNotFoundException();
      }
      return paymentDTO.getUid();
    });
  }


}

package com.kion.bunga.services;

import com.kion.bunga.domain.PaymentDTO;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public interface PersistenceService {

  CompletableFuture<Boolean> persistAsync(PaymentDTO paymentDTO);
  boolean persist(PaymentDTO paymentDTO);
  Set<PaymentDTO> getCached();
}

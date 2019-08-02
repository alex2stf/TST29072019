package com.kion.bunga.services;

import com.kion.bunga.domain.PaymentDTO;
import com.kion.bunga.domain.Proto.TransactionResponse;
import java.util.Set;

public interface PersistenceService {

  TransactionResponse persist(PaymentDTO paymentDTO);
  Set<PaymentDTO> getCached();
}

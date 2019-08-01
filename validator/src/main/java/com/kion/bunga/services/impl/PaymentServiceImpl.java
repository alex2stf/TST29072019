package com.kion.bunga.services.impl;

import com.kion.bunga.domain.Convertors;
import com.kion.bunga.domain.PaymentDTO;
import com.kion.bunga.domain.PaymentRequest;
import com.kion.bunga.errors.RestException;
import com.kion.bunga.services.PaymentService;
import com.kion.bunga.services.PersistenceService;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {




  private final PersistenceService persistenceService;

  public PaymentServiceImpl(PersistenceService persistenceService) {
    this.persistenceService = persistenceService;
  }


  public String create(PaymentRequest request) throws RestException {
    PaymentDTO paymentDTO = Convertors.fromRequest(request);
    persistenceService.persistAsync(paymentDTO);
    return paymentDTO.getUid();
  }


}

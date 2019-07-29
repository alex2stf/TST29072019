package com.kion.bunga.services.impl;

import com.kion.bunga.domain.PaymentDTO;
import com.kion.bunga.domain.PaymentRequest;
import com.kion.bunga.errors.RestException;
import com.kion.bunga.services.PaymentService;
import org.ehcache.Cache;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {


  private final Cache<String, PaymentDTO> paymentCache;

  public PaymentServiceImpl(Cache<String, PaymentDTO> paymentCache) {
    this.paymentCache = paymentCache;
  }


  public String create(PaymentRequest request) throws RestException {

    //TODO call to persist
    //if failure, put in queue, send to other friend servers
    PaymentDTO paymentDTO = PaymentDTO.fromRequest(request);

    paymentCache.put(paymentDTO.getUid(), paymentDTO);
    return paymentDTO.getUid();
  }
}

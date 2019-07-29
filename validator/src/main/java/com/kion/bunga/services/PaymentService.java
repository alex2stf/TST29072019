package com.kion.bunga.services;

import com.kion.bunga.domain.PaymentRequest;
import com.kion.bunga.errors.RestException;

public interface PaymentService {

  String create(PaymentRequest request) throws RestException;
}

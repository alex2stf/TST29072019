package com.kion.bunga.services;

import com.kion.bunga.domain.PaymentRequest;
import com.kion.bunga.errors.RestException;
import java.util.concurrent.CompletableFuture;

public interface PaymentService {

  CompletableFuture<String> create(PaymentRequest request);
}

package com.kion.bunga.services;

import com.kion.bunga.domain.Proto.Payment;
import com.kion.bunga.domain.Proto.TransactionResponse;

public interface TransactionService {

  TransactionResponse save(Payment payment);
}

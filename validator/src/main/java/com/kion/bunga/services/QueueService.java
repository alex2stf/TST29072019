package com.kion.bunga.services;

import com.kion.bunga.domain.PaymentDTO;

public interface QueueService {
  void send(PaymentDTO paymentDTO);
}

package com.kion.bunga.domain;

import com.kion.bunga.domain.Proto.Payment;
import com.kion.bunga.domain.Proto.TRANSACTION_TYPE;
import java.util.UUID;

public class Convertors {
  public static Proto.Payment toProto(PaymentDTO dto){
    return Payment.newBuilder()
        .setReceiver(dto.getReceiver())
        .setSender(dto.getSender())
        .setAmount(dto.getAmount())
        .setUid(dto.getUid())
        .setName(dto.getName())
        .setDescripton(dto.getDescription())
        .setTransactionType(TRANSACTION_TYPE.valueOf(dto.getTransactionType().name()))
        .build();
  }

  public static PaymentDTO fromRequest(PaymentRequest request){
    PaymentDTO paymentDTO = new PaymentDTO();
    paymentDTO.setAmount(request.getAmount());
    paymentDTO.setReceiver(request.getReceiver());
    paymentDTO.setSender(request.getSender());
    paymentDTO.setName(request.getName());
    paymentDTO.setDescription(request.getDescription());
    paymentDTO.setTransactionType(request.getTransactionType());
    String uid = UUID.randomUUID().toString() + "-" + System.currentTimeMillis();
    paymentDTO.setUid(uid);
    return paymentDTO;
  }
}

package com.kion.bunga.domain;

import com.kion.bunga.domain.Proto.Payment;
import com.kion.bunga.domain.Proto.TRANSACTION_TYPE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Convertors {

  private static final Logger log = LoggerFactory.getLogger(Convertors.class);

  public static Proto.Payment toProto(PaymentDTO dto){
    TRANSACTION_TYPE transaction_type = null;

    try {
      //dto string can be corrupted, so ignore theese kind of messages
      transaction_type = TRANSACTION_TYPE.valueOf(dto.getTransactionType());
    } catch (Exception ex){
      log.error("Failed to parse " + dto, ex);
      return null;
    }

    return Payment.newBuilder()
        .setReceiver(dto.getReceiver())
        .setSender(dto.getSender())
        .setAmount(dto.getAmount())
        .setUid(dto.getUid())
        .setName(dto.getName())
        .setDescripton(dto.getDescription())
        .setTransactionType(transaction_type)
        .build();
  }
}

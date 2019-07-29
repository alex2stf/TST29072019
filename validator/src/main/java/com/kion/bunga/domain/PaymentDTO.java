package com.kion.bunga.domain;

import com.kion.bunga.domain.Proto.Payment;
import java.util.UUID;

public class PaymentDTO extends PaymentRequest {

  private String uid;



  public String getUid() {
    return uid;
  }

  public void setUid(String uid) {
    this.uid = uid;
  }

  public static PaymentDTO fromRequest(PaymentRequest request){
    PaymentDTO paymentDTO = new PaymentDTO();
    paymentDTO.setAmount(request.getAmount());
    paymentDTO.setReceiver(request.getReceiver());
    paymentDTO.setSender(request.getSender());
    String uid = UUID.randomUUID().toString() + "-" + System.currentTimeMillis();
    paymentDTO.setUid(uid);
    return paymentDTO;
  }


  public Proto.Payment toProto(){
    return Payment.newBuilder()
        .setReceiver(getReceiver())
        .setSender(getSender())
        .setAmount(getAmount())
        .setUid(getUid())
        .build();
  }
}

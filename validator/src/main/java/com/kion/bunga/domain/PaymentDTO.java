package com.kion.bunga.domain;

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
}

package com.kion.bunga.domain;

import com.kion.bunga.domain.Proto.Payment;
import com.kion.bunga.domain.Proto.TRANSACTION_TYPE;
import java.util.UUID;

public class PaymentDTO extends PaymentRequest {

  private String uid;



  public String getUid() {
    return uid;
  }

  public void setUid(String uid) {
    this.uid = uid;
  }





}

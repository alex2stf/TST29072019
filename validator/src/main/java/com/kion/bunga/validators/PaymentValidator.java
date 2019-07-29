package com.kion.bunga.validators;

import com.kion.bunga.domain.PaymentRequest;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class PaymentValidator implements Validator {

  @Override
  public boolean supports(Class<?> aClass) {
    return PaymentRequest.class.isAssignableFrom(aClass);
  }

  @Override
  public void validate(Object o, Errors errors) {
    if (! (o instanceof PaymentRequest) ){
      return;
    }

    //TODO add enum based validation
    PaymentRequest request = (PaymentRequest) o;
//    if (errors != null){
//      errors.reject(BCRErrorKey.BUSINESS_LOGIC_INVALID.name(), message);
//    }

  }
}

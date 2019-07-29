package com.kion.bunga.validators;

import com.kion.bunga.domain.PaymentRequest;
import com.kion.bunga.domain.TransactionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class PaymentValidator implements Validator {

  private static final int[] CONTROL_LIST = new int[]{2, 7, 9, 1, 4, 6, 3, 5, 8, 2, 7, 9};

  private static final Logger log = LoggerFactory.getLogger(PaymentValidator.class);

  private static final String SAME_IDS_ERROR = "sender and receiver cannot be the same";

  @Override
  public boolean supports(Class<?> aClass) {
    return PaymentRequest.class.isAssignableFrom(aClass);
  }

  @Override
  public void validate(Object o, Errors errors) {
    if (!(o instanceof PaymentRequest)) {
      return;
    }
    PaymentRequest request = (PaymentRequest) o;
    if (request.getSender().equals(request.getReceiver())) {
      errors.reject(SAME_IDS_ERROR, SAME_IDS_ERROR);
      log.error(SAME_IDS_ERROR);
      return;
    }

    TransactionType transactionType = request.getTransactionType();

    switch (transactionType) {
      case IBAN_TO_IBAN:
        validateField(request.getSender(), false, "sender", errors, transactionType);
        validateField(request.getReceiver(), false, "receiver", errors, transactionType);
        break;

      case IBAN_TO_WALLET:
        validateField(request.getSender(), false, "sender", errors, transactionType);
        validateField(request.getReceiver(), true, "receiver", errors, transactionType);
        break;

      case WALLET_TO_IBAN:
        validateField(request.getSender(), true, "sender", errors, transactionType);
        validateField(request.getReceiver(), false, "receiver", errors, transactionType);
        break;


      case WALLET_TO_WALLET:
        validateField(request.getSender(), true, "sender", errors, transactionType);
        validateField(request.getReceiver(), true, "receiver", errors, transactionType);
        break;
    }

  }

  private void validateField(String input, boolean asCnp, String who, Errors errors, TransactionType transactionType){
    String msg1 = null;
    String msg2 = null;
    if (asCnp && !isValidPersonalCode(input)){
      msg1 = "invalid " + who + " CNP";
      msg2 = "Invalid " + who +" CNP for transaction type " + transactionType;
    }
    else if(!asCnp && !isValidIBAN(input)) {
      msg1 = "invalid " + who + " IBAN";
      msg2 = "Invalid " + who +" IBAN for transaction type " + transactionType;
    }

    if (msg1 != null && msg2 != null){
      errors.reject(msg1, msg2);
      log.error(msg2);
    }
  }

  /**
   * simple IBAN validator
   * @param input
   * @return
   */
  public boolean isValidIBAN(String input){
    return StringUtils.hasText(input) && input.length() == 24;
  }


  public boolean isValidPersonalCode(String value) {
    if (!StringUtils.hasText(value) || value.length() != 13) {
      return false;
    }
    int sum = 0;
    for (int i = 0; i < 12; i++) {
      char c = value.charAt(i);
      int x = c - '0';
      int mult = x * CONTROL_LIST[i];
      sum += mult;
    }

    int expectedControl = (sum % 11) < 10 ? (sum % 11) : 1;
    int actualControl = value.charAt(12) - '0';
    return expectedControl == actualControl;
  }

}

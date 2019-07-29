package com.kion.bunga.web.rest;


import com.kion.bunga.domain.PaymentRequest;
import com.kion.bunga.errors.RestException;
import com.kion.bunga.services.PaymentService;
import com.kion.bunga.validators.PaymentValidator;
import javax.validation.Valid;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PaymentController {

  private final PaymentService paymentService;

  public PaymentController(PaymentService paymentService) {
    this.paymentService = paymentService;
  }

  @PostMapping("/payment")
  public String create(@RequestBody @Valid PaymentRequest request) throws RestException {
    return paymentService.create(request);
  }

  //TODO similar method with protobuf for intercommunication

  /**
   * setup request validator
   * @param binder
   */
  @InitBinder
  protected void initBinder(WebDataBinder binder) {
    binder.addValidators(new PaymentValidator());
  }
}

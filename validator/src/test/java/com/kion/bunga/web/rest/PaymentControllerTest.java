package com.kion.bunga.web.rest;

import com.kion.bunga.domain.PaymentRequest;
import com.kion.bunga.domain.TransactionType;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpClientErrorException.BadRequest;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PaymentControllerTest {


  @LocalServerPort
  private int port;


  private String getPaymentEndpoint(){
    return "http://localhost:" + port + "/api/payment";
  }

  @Test
  public void create() {

    //Arrange
    RestTemplate restTemplate = new RestTemplate();
    PaymentRequest request = new PaymentRequest();
    request.setAmount(12);

    //Act
    String response;
    try {
      response = restTemplate.postForObject(getPaymentEndpoint(), request, String.class);
      Assert.fail("should throw BadRequest");
    } catch (HttpClientErrorException ex){
    //Assert
      Assert.assertTrue(ex instanceof BadRequest);
    }


    //Arrange
    request.setSender("anything");
    request.setReceiver("anything");
    request.setTransactionType(TransactionType.WALLET_TO_IBAN);

    //Act
    try {
      response = restTemplate.postForObject(getPaymentEndpoint(), request, String.class);
      Assert.fail("should throw BadRequest");
    } catch (HttpClientErrorException ex){
      //Assert
      Assert.assertTrue(ex instanceof BadRequest);
    }


    //Arrange (valid call)
    request.setSender("1851102430039");
    request.setReceiver("123456789012345678901234");
    request.setTransactionType(TransactionType.WALLET_TO_IBAN);


    response = restTemplate.postForObject(getPaymentEndpoint(), request, String.class);



  }
}

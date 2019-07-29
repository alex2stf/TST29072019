package com.kion.bunga.web.rest;

import com.kion.bunga.domain.PaymentRequest;
import com.kion.bunga.domain.TransactionType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PaymentControllerTest {


  @LocalServerPort
  private int port;



  @Test
  public void create() {

    PaymentRequest request = new PaymentRequest();
    request.setAmount(12);
    request.setSender("2333");
    request.setReceiver("dasdasd");
    request.setTransactionType(TransactionType.IBAN_TO_IBAN);

    RestTemplate restTemplate = new RestTemplate();
    String r = restTemplate.postForObject("http://localhost:" + port + "/api/payment", request, String.class);

    System.out.println(r);
  }
}

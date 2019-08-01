package com.kion.bunga.web.rest;

import com.google.protobuf.Message;
import com.kion.bunga.domain.Proto.Payment;
import com.kion.bunga.domain.Proto.TRANSACTION_TYPE;
import com.kion.bunga.domain.Proto.TransactionResponse;
import java.util.Arrays;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.converter.protobuf.ProtobufHttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TransactionControllerTest {

  @LocalServerPort
  private int port;

  @Test
  public void test(){
    RestTemplate restTemplate =  new RestTemplate(Arrays.asList(new ProtobufHttpMessageConverter()));

    Payment payment = Payment.newBuilder().setAmount(23).setSender("dsdadsd")
        .setName("name")
        .setDescripton("desc")
        .setTransactionType(TRANSACTION_TYPE.IBAN_TO_IBAN)
        .setSender("dsadasdas").build();

    HttpHeaders headers = new HttpHeaders();
    headers.set( "Accept", "application/rest" );
    headers.setContentType(ProtobufHttpMessageConverter.PROTOBUF);
//      headers.put("Authorization", Arrays.asList(new String[]{"F_,8YF,>uYxcI0|dM7XKT3K"}));
//      headers.put("X-Manager", Arrays.asList(new String[]{"F_,8YF,>uYxcI0|dM7XKT3K"}));

    HttpEntity<Message> entity = new HttpEntity<>(payment, headers);
    restTemplate.postForObject("http://localhost:" + port + "/api/transaction", payment, TransactionResponse.class);
//    restTemplate.put("http://localhost:" + port + "/api/transaction", entity);
  }
}
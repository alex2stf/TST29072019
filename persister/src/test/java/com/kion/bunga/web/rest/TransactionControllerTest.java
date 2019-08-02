package com.kion.bunga.web.rest;

import com.google.protobuf.Message;
import com.kion.bunga.config.ApplicationProperties;
import com.kion.bunga.domain.Proto.Payment;
import com.kion.bunga.domain.Proto.TRANSACTION_STATUS;
import com.kion.bunga.domain.Proto.TRANSACTION_TYPE;
import com.kion.bunga.domain.Proto.TransactionResponse;
import com.kion.bunga.entities.Account;
import com.kion.bunga.entities.Transaction;
import com.kion.bunga.repositories.AccountRepository;
import com.kion.bunga.repositories.TransactionRepository;
import java.util.Arrays;
import java.util.Optional;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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

  @Autowired
  ApplicationProperties applicationProperties;

  @Autowired
  AccountRepository accountRepository;


  @Autowired
  TransactionRepository transactionRepository;

  final RestTemplate restTemplate =  new RestTemplate(Arrays.asList(new ProtobufHttpMessageConverter()));

  @Before
  public void setup(){
    for (int i = 1; i < 10; i++){
      Optional<Account> accountOptional = accountRepository.findById((long) i);
      if (!accountOptional.isPresent()){
        Account account = new Account();
        account.setId((long) i);
        account.setName("test_account_" + i);
        account.setCnp("test_cnp_" + i);
        account.setIban("test_iban_" + i);
        accountRepository.save(account);
      }

    }
  }

  private TransactionResponse request(Payment payment){
    HttpHeaders headers = new HttpHeaders();
    headers.set( "Accept", "application/x-protobuf" );
    headers.setBasicAuth(applicationProperties.getUsername(), applicationProperties.getPassword());
    headers.setContentType(ProtobufHttpMessageConverter.PROTOBUF);

    HttpEntity<Message> entity = new HttpEntity<>(payment, headers);

    return restTemplate.postForObject("http://localhost:" + port + "/transaction", entity, TransactionResponse.class);
  }

  @Test
  public void testIbanToWallet(){
    //Arrange
    Payment payment = Payment.newBuilder()
        .setAmount(20)
        .setSender("test_iban_4")
        .setReceiver("test_cnp_5")
        .setTransactionType(TRANSACTION_TYPE.IBAN_TO_WALLET)
        .setUid("J")
        .build();

    //Act
    TransactionResponse response = request(payment);

    //Assert
    Assert.assertTrue(transactionRepository.findById(payment.getUid()).isPresent());
  }

  @Test
  public void testMissingAccount(){
    TransactionResponse response;

    //Arrange
    Payment payment = Payment.newBuilder()
        .setAmount(23).setSender("dsdadsd")
        .setName("name")
        .setDescripton("desc")
        .setUid("xxx")
        .setTransactionType(TRANSACTION_TYPE.IBAN_TO_IBAN)
        .setSender("dsadasdas").build();

    //Act
    response = request(payment);

    //Assert
    Assert.assertEquals(TRANSACTION_STATUS.DENIED, response.getStatus());
  }
}
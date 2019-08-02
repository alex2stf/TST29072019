package com.kion.bunga.services.impl;

import com.google.protobuf.Message;
import com.kion.bunga.config.PersistenceProperties;
import com.kion.bunga.domain.Convertors;
import com.kion.bunga.domain.PaymentDTO;
import com.kion.bunga.domain.Proto.TRANSACTION_STATUS;
import com.kion.bunga.domain.Proto.TransactionResponse;
import com.kion.bunga.services.PersistenceService;
import com.kion.bunga.services.QueueService;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import org.ehcache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.converter.protobuf.ProtobufHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.web.client.RestTemplate;

@Service
public class PersistenceServiceImpl implements PersistenceService {

  private final Cache<String, PaymentDTO> paymentCache;
  private final PersistenceProperties persistenceProperties;
  private final QueueService queueService;
  private final RestTemplate restTemplate =  new RestTemplate(Arrays.asList(new ProtobufHttpMessageConverter()));
  private static final Logger log = LoggerFactory.getLogger(PersistenceServiceImpl.class);

  public PersistenceServiceImpl(Cache<String, PaymentDTO> paymentCache, PersistenceProperties persistenceProperties, QueueService queueService) {
    this.paymentCache = paymentCache;
    this.persistenceProperties = persistenceProperties;
    this.queueService = queueService;
  }

  @Override
  public CompletableFuture<Boolean> persistAsync(PaymentDTO paymentDTO) {
    return CompletableFuture.supplyAsync(() -> persist(paymentDTO));
  }




  public boolean persist(PaymentDTO paymentDTO){

    boolean success = true;
    String url = persistenceProperties.getUrl() + "/transaction";
    Message message = Convertors.toProto(paymentDTO);

    try {
      HttpHeaders headers = new HttpHeaders();
      headers.setBasicAuth(persistenceProperties.getUsername(), persistenceProperties.getPassword());
      HttpEntity<Message> entity = new HttpEntity<>(message, headers);
      TransactionResponse response = restTemplate.postForObject(url, entity, TransactionResponse.class);
      if (response.getStatus().equals(TRANSACTION_STATUS.RETRY)){
        success = false;
      }
      log.info("transaction {} status {}", paymentDTO.getUid(), response.getStatus().name());
    } catch (Throwable ex){
      success = false;
      ex.printStackTrace();
      log.error("transaction " + paymentDTO.getUid() + " failed because " + ex.getMessage(), ex);
    }

   //if transaction was not made with success:
    if (!success){
      //put in queue
      queueService.send(paymentDTO);

      //add it to cache
      synchronized (paymentCache){
        paymentCache.putIfAbsent(paymentDTO.getUid(), paymentDTO);
      }
    }
    else {
      synchronized (paymentCache){
        try {
          paymentCache.remove(paymentDTO.getUid());
        } catch (Exception e){
          log.error("Failed to remove " + paymentDTO.getUid() + " from cache", e);
        }
      }
    }

    return true;
  }

  public Set<PaymentDTO> getCached(){
    Set<PaymentDTO> copy = new HashSet<>();
    synchronized (paymentCache){
      for (Cache.Entry<String, PaymentDTO> entry: paymentCache){
        copy.add(entry.getValue());
      }
    }
    return copy;
  }
}

package com.kion.bunga.tasks;

import com.kion.bunga.domain.PaymentDTO;
import com.kion.bunga.services.PersistenceService;
import com.kion.bunga.services.impl.PersistenceServiceImpl;
import java.util.Set;
import java.util.function.Consumer;

import org.ehcache.Cache.Entry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
@ComponentScan({"com.bunga.kion"})
public class RetryTask {

  private static final Logger log = LoggerFactory.getLogger(RetryTask.class);

  private final PersistenceService persistenceService;

  public RetryTask(PersistenceService persistenceService) {
    this.persistenceService = persistenceService;
  }

  @Scheduled(cron = "${crons.retryCheck}")
  public void retry(){
    Set<PaymentDTO> paymentDTOSet = persistenceService.getCached();
    for (PaymentDTO paymentDTO: paymentDTOSet){
      log.info("Retry " + paymentDTO.getUid());
      persistenceService.persist(paymentDTO);
    }
  }
}

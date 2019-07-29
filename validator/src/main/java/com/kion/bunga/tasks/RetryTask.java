package com.kion.bunga.tasks;

import com.kion.bunga.domain.PaymentDTO;
import java.util.Date;
import java.util.function.Consumer;
import org.ehcache.Cache;
import org.ehcache.Cache.Entry;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
@ComponentScan({"com.bunga.kion"})
public class RetryTask {

  private final Cache<String, PaymentDTO> paymentCache;

  public RetryTask(Cache<String, PaymentDTO> paymentCache) {
     this.paymentCache = paymentCache;
  }

  //TODO retry failed calls

  @Scheduled(cron = "${crons.retryCheck}")
  public void retry(){

   paymentCache.forEach(new Consumer<Entry<String, PaymentDTO>>() {
     @Override
     public void accept(Entry<String, PaymentDTO> entry) {
       System.out.println(entry.getKey() + " = " + entry.getValue());
     }
   });

    System.out.println("__________________________________");

//    System.out.println("retry at " + new Date());
  }
}

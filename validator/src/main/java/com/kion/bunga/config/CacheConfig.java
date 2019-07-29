package com.kion.bunga.config;


import com.kion.bunga.domain.PaymentDTO;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class CacheConfig {

  public static final String PAYMENTS = "payments";

  @Bean
  public CacheManager cacheManager(){
    CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
        .withCache(PAYMENTS, CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, PaymentDTO.class, ResourcePoolsBuilder.heap(2000)))
        .build();
    cacheManager.init();
    return cacheManager;
  }

  @Bean
  public Cache<String, PaymentDTO> paymentCache(CacheManager cacheManager){
    return cacheManager.getCache(PAYMENTS, String.class, PaymentDTO.class);
  }
}

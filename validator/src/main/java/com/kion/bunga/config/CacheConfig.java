package com.kion.bunga.config;


import com.kion.bunga.domain.PaymentDTO;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.core.config.DefaultConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.terracotta.offheapstore.util.MemoryUnit;

@Configuration
@EnableScheduling
public class CacheConfig {

  public static final String PAYMENTS = "payments";

  @Bean
  public CacheManager cacheManager(){

//    org.ehcache.config.Configuration cacheManagerConfig = new EhCacheRun()
//        .diskStore(new DiskStoreConfiguration()
//            .path("/path/to/store/data"));
//    CacheConfiguration cacheConfig = new CacheConfiguration()
//        .name("my-cache")
//        .maxBytesLocalHeap(16, MemoryUnit.MEGABYTES)
//        .maxBytesLocalOffHeap(256, MemoryUnit.MEGABYTES)
//        .persistence(new PersistenceConfiguration().strategy(Strategy.LOCALTEMPSWAP));
//
//    cacheManagerConfig.addCache(cacheConfig);
//
//    CacheManager cacheManager = new CacheManager(cacheManagerConfig);
//    Ehcache myCache = cacheManager.getEhcache("my-cache");
//


    CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
        .withCache(PAYMENTS, CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, PaymentDTO.class, ResourcePoolsBuilder.heap(2000)))
        .build();


    cacheManager.init();
    return cacheManager;
  }

  @Bean
  public Cache<String, PaymentDTO> paymentCache(CacheManager cacheManager){
    Cache<String, PaymentDTO> response = cacheManager.getCache(PAYMENTS, String.class, PaymentDTO.class);
    return response;
  }
}

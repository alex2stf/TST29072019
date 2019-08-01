package com.kion.bunga.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;

@Configuration
public class RedisConfiguration {
  @Value("${spring.redis.host}")
  private String hostname;

  @Value("${spring.redis.port}")
  private int port;


  @Bean
  public Jedis jedis(){
    Jedis jedis = null;
    try {
      jedis = new Jedis(hostname, port);
      jedis.connect();
    } catch (Exception ex){

    }

    return jedis;
  }
}

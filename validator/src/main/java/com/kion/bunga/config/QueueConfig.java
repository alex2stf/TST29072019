package com.kion.bunga.config;

import com.rabbitmq.client.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueConfig {

  @Bean
  public ConnectionFactory connectionFactory(QueueProperties queueProperties){
    ConnectionFactory connectionFactory = new ConnectionFactory();

    try {

      connectionFactory.setUsername(queueProperties.getUsername());
      connectionFactory.setPassword(queueProperties.getPassword());

      if (queueProperties.getUrl() != null){
        connectionFactory.setUri(queueProperties.getUrl());
      } else {
        connectionFactory.setPort(queueProperties.getPort());
        connectionFactory.setHost(queueProperties.getHost());
        connectionFactory.setVirtualHost(queueProperties.getVirtualHost());
      }
    }catch (Exception ex){
      //TODO
      System.out.println(ex);
      ex.printStackTrace();
    }


    return connectionFactory;
  }
}

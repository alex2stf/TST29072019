package com.kion.bunga.config;

import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueConfig {

  private static final Logger log = LoggerFactory.getLogger(QueueConfig.class);

  @Bean
  public ConnectionFactory connectionFactory(QueueProperties queueProperties) {
    ConnectionFactory connectionFactory = new ConnectionFactory();
    try {
      connectionFactory.setUsername(queueProperties.getUsername());
      connectionFactory.setPassword(queueProperties.getPassword());

      if (queueProperties.getUrl() != null) {
        connectionFactory.setUri(queueProperties.getUrl());
      } else {
        connectionFactory.setPort(queueProperties.getPort());
        connectionFactory.setHost(queueProperties.getHost());
        connectionFactory.setVirtualHost(queueProperties.getVirtualHost());
      }
    } catch (Exception ex) {
      log.error("Failed to connect to queue ", ex);
    }

    return connectionFactory;
  }
}

package com.kion.bunga.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kion.bunga.Main;
import com.kion.bunga.domain.PaymentDTO;
import com.kion.bunga.services.QueueService;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class QueueServiceImpl implements QueueService {

  private static final String QUEUE_NAME = "payments";

  private final ConnectionFactory connectionFactory;
  private Channel channel;


  private static final Logger log = LoggerFactory.getLogger(QueueServiceImpl.class);

  public QueueServiceImpl(ConnectionFactory connectionFactory) {
    this.connectionFactory = connectionFactory;
    channel = createNewChannel();
  }


  private Channel createNewChannel(){
    if (connectionFactory == null){
      return null;
    }
    try {
      Connection connection = connectionFactory.newConnection();
      Channel channel = connection.createChannel();
      channel.queueDeclare(QUEUE_NAME, true, false, false, null);
      return channel;
    } catch (Exception e) {
      log.error("Failed to connect to Rabbit. Queue disabled");
      return null;
    }
  }

  public void send(PaymentDTO paymentDTO){
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      channel.basicPublish("", QUEUE_NAME, null, objectMapper.writeValueAsString(paymentDTO).getBytes());
    } catch (Exception e) {
      log.error("Failed to put in queue " + paymentDTO.getUid(), e);
    }
  }
}

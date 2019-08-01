package com.kion.bunga.tasks;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.kion.bunga.domain.Convertors;
import com.kion.bunga.domain.PaymentDTO;
import com.kion.bunga.domain.Proto.Payment;
import com.kion.bunga.services.TransactionService;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@ComponentScan({"com.bunga.kion"})
public class QueueConsumer {


  private static final String QUEUE_NAME = "payments";


  private static final Logger log = LoggerFactory.getLogger(QueueConsumer.class);

  private final ConnectionFactory connectionFactory;
  private final TransactionService transactionService;

  private final DeliverCallback deliverCallback = new DeliverCallback() {
    public void handle(String consumerTag, Delivery delivery) throws IOException {
      String payload = new String(delivery.getBody(), "UTF-8");
      log.info("read from queue {}", payload);
      ObjectMapper objectMapper = new ObjectMapper();
      PaymentDTO paymentDTO = objectMapper.readValue(payload, PaymentDTO.class);
      Payment message = Convertors.toProto(paymentDTO);
      if (message != null){
        transactionService.save(message);
      }
      payload.intern(); //intern payload since queue can send twice the same message
    }
  };

  private Channel channel;



  public QueueConsumer(ConnectionFactory connectionFactory, TransactionService transactionService) {
    this.connectionFactory = connectionFactory;
    this.transactionService = transactionService;
    setupConsumer();
  }


  @Async
  public void setupConsumer() {
    Connection connection = null;
    try {
      connection = connectionFactory.newConnection();
      channel = connection.createChannel();
      channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> log.error("cancelled " + consumerTag));
    } catch (Exception ex){
      log.error("Failed to setup queue consumer", ex);
      if (connection != null){
        try {
          connection.close();
        } catch (IOException e) {
          log.error("Failed to close queue connection", e);
        }
      }

      if (channel != null){
        try {
          channel.close();
        } catch (Exception ce) {
          log.error("Failed to close queue channel", ce);
        }
      }
    }

  }


}
